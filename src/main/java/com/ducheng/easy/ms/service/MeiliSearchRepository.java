package com.ducheng.easy.ms.service;

import com.ducheng.easy.ms.anotation.CustomIndex;
import com.ducheng.easy.ms.entity.MeiliSearchResult;
import com.meilisearch.sdk.*;
import com.meilisearch.sdk.json.GsonJsonHandler;
import com.meilisearch.sdk.model.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class MeiliSearchRepository<T> implements InitializingBean, DocumentOperations<T> {

    /**
     *  初始化气的时候默认给索引的值
     */
    private Index index;
    private Class<T> tClass;

    private static   GsonJsonHandler jsonHandler = new GsonJsonHandler();

    @Resource
    private Client client;


    @Override
    public T get(String identifier) {
        T t = null;
        try {
            t = index.getDocument(identifier,tClass);
        } catch (Exception e) {
        }
        return t;
    }

    @Override
    public List<T> list() {
        Results<T> t = new Results<>();
        try {
            t = index.getDocuments(tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList(t.getResults());
    }

    @Override
    public List<T> list(int limit) {
        Results<T> t = null;
        try {
            DocumentsQuery documentsQuery = new DocumentsQuery();
            documentsQuery.setOffset(0);
            documentsQuery.setLimit(limit);
            t = index.getDocuments(documentsQuery,tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList(t.getResults());
    }

    @Override
    public List<T> list(int offset, int limit) {
        List<T> list = list(offset + limit);
        return list.subList(offset, list.size() - 1);
    }

    @Override
    public long add(T document) {
        List<T> list = Collections.singletonList(document);
        return add(list);
    }

    @Override
    public long update(T document) {
        List<T> list = Collections.singletonList(document);
        return update(list);
    }

    @Override
    public long add(List<T> documents) {
        TaskInfo taskInfo = null ;
        try {
            taskInfo = index.addDocuments(jsonHandler.encode(documents));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return taskInfo.getTaskUid();
    }

    @Override
    public long update(List<T> documents) {
        TaskInfo taskInfo = null ;
        try {
            taskInfo = index.updateDocuments(jsonHandler.encode(documents));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return taskInfo.getTaskUid();
    }


    @Override
    public long delete(String identifier) {
        TaskInfo taskInfo = null ;
        try {
            taskInfo = index.deleteDocument(identifier);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return taskInfo.getTaskUid();
    }

    @Override
    public long deleteBatch(String... documentsIdentifiers) {
        TaskInfo taskInfo = null ;
        try {
            taskInfo = index.deleteDocuments(Arrays.asList(documentsIdentifiers));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return taskInfo.getTaskUid();
    }


    @Override
    public List<T> query(String query)  {
        MeiliSearchResult<T>  meiliSearchResult = new MeiliSearchResult<>();
        try {
            String rawSearch = index.rawSearch(query);
            meiliSearchResult = jsonHandler.decode(rawSearch, MeiliSearchResult.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return meiliSearchResult.getHits();
    }

    @Override
    public List<T> query(SearchRequest searchRequest) {
        List<T> list =  new ArrayList<>();
        try {
            Searchable search = index.search(searchRequest);
            ArrayList<HashMap<String, Object>> hits = search.getHits();
            String encode = jsonHandler.encode(hits);
            list = jsonHandler.decode(encode, List.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public long deleteAll() {
        TaskInfo taskInfo = null ;
        try {
            taskInfo = index.deleteAllDocuments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return taskInfo.getTaskUid();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initIndex();
    }

    /**
     * 初始化索引信息
     * @throws Exception
     */
    private void initIndex() throws Exception {
        Class<? extends MeiliSearchRepository> clazz = getClass();
        tClass = (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        CustomIndex annoIndex = tClass.getAnnotation(CustomIndex.class);
        String uid = annoIndex.uid();
        String primaryKey = annoIndex.primaryKey();
        if (StringUtils.isEmpty(uid)) {
            uid = tClass.getSimpleName().toLowerCase();
        }
        if (StringUtils.isEmpty(primaryKey)) {
            primaryKey = "id";
        }
        Index index ;
        try {
            //如果不指定索引， 默认就使用表名称
             index = client.getIndex(uid);
        }catch (Exception e) {
            index = null;
        }
        if (ObjectUtils.isEmpty(index)) {
            TaskInfo taskInfo = client.createIndex(uid, primaryKey);
            index=  client.getIndex(uid);
        }
        this.index = index;
    }

}
