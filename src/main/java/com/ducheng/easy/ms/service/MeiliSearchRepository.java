package com.ducheng.easy.ms.service;

import com.ducheng.easy.ms.anotation.CustomIndex;
import com.meilisearch.sdk.*;
import com.ducheng.easy.ms.json.JsonHandler;
import com.ducheng.easy.ms.json.SearchResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MeiliSearchRepository<T> implements InitializingBean, DocumentOperations<T> {

    /**
     *  初始化气的时候默认给索引的值
     */
    private Index index;
    private Class<T> tClass;
    private JsonHandler jsonHandler = new JsonHandler();

    @Resource
    private Client client;


    @Override
    public T get(String identifier) {
        String document;
        try {
            document = index.getDocument(identifier);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonHandler.decode(document, tClass);
    }

    @Override
    public List<T> list() {
        String documents;
        try {
            documents = index.getDocuments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonHandler.listDecode(documents, tClass);
    }

    @Override
    public List<T> list(int limit) {
        String documents;
        try {
            documents = index.getDocuments(limit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonHandler.listDecode(documents, tClass);
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
    public long add(List documents) {
        String updates ;
        try {
            updates = index.addDocuments(jsonHandler.encode(documents));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getTaskUid(updates);
    }

    @Override
    public long update(List<T> documents) {
        String updates ;
        try {
            updates = index.updateDocuments(jsonHandler.encode(documents));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getTaskUid(updates);
    }


    @Override
    public long delete(String identifier) {
        String s;
        try {
            s = index.deleteDocument(identifier);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getTaskUid(s);
    }

    @Override
    public long deleteBatch(String... documentsIdentifiers) {
        String s;
        try {
            s = index.deleteDocuments(Arrays.asList(documentsIdentifiers));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getTaskUid(s);
    }

    @Override
    public long deleteAll() {
        String s;
        try {
            s = index.deleteAllDocuments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getTaskUid(s);
    }


    @Override
    public SearchResult<T> search(String q) {
        String result;
        try {
            result = index.search(q);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonHandler.resultDecode(result, tClass);
    }

    @Override
    public SearchResult<T> search(String q, int offset, int limit) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setQ(q);
        searchRequest.setOffset(offset);
        searchRequest.setLimit(limit);
        return search(searchRequest);
    }

    @Override
    public SearchResult<T> search(SearchRequest sr) {
        String result;
        try {
            result = index.search(sr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonHandler.resultDecode(result, tClass);
    }

    @Override
    public Settings getSettings() {
        try {
            return index.getSettings();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UpdateStatus updateSettings(Settings settings) {
        try {
            return index.updateSettings(settings);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UpdateStatus resetSettings() {
        try {
            return index.resetSettings();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UpdateStatus getUpdate(int updateId) {
        try {
            return index.getUpdate(updateId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UpdateStatus[] getUpdates() {
        try {
            return index.getUpdates();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private long getTaskUid(String s) {
        Map map = jsonHandler.decode(s, Map.class);
        return ((Double) map.get("taskUid")).longValue();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        initIndex();
    }

    public Index getIndex() {
        return index;
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
        //如果不指定索引， 默认就使用表名称
        Index index = client.getIndex(uid);
        if (ObjectUtils.isEmpty(index)) {
            index = client.createIndex(uid, primaryKey);
        }
        this.index = index;
    }
}
