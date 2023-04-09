package com.ducheng.easy.ms;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.Key;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = MeiLiSearchApplication.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MeiLiSearchTest {

    @Autowired
    private BookEntityRepository bookEntityRepository;

    @Resource
    private Client client;

    @Test
    public void addEntity() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookName("我要朝鲜语");
        bookEntity.setId("400");
        long result = bookEntityRepository.add(bookEntity);
        System.out.println("添加书籍返回结果:" + result);
    }

    @Test
    public void update() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookName("我要朝弟弟鲜语");
        bookEntity.setId("400");
        long update = bookEntityRepository.update(bookEntity);
        System.out.println("添加书籍返回结果:" + update);
    }

    @Test
    public void list() {
        List<BookEntity> list = bookEntityRepository.list();
        list.forEach(x-> {
            System.out.println("返回的数据是:" + x.toString());
        });
    }

    @Test
    public void deleteAll() {
        long deleteAll = bookEntityRepository.deleteAll();
        System.out.println("删除数据返回长度："+deleteAll);
    }

    @Test
    public void deleteById() {
        long deleteAll = bookEntityRepository.delete("400");
        System.out.println("删除数据返回长度："+deleteAll);
    }

    @Test
    public void get() {
        BookEntity bookEntity = bookEntityRepository.get("400");
        System.out.println("返回的数据是:" + bookEntity.toString());
    }


    @Test
    public void getSearchRwe() throws MeilisearchException {
        List<BookEntity> entities = bookEntityRepository.query("我要");
        System.out.println("返回的数据是:" +entities);
    }


    @Test
    public void search() throws MeilisearchException {
        SearchRequest searchRequest = new SearchRequest("我要");
        List<BookEntity> entities = bookEntityRepository.query(searchRequest);
        System.out.println("返回的数据是:" +entities);
    }

    @Test
    public void  createKey() throws ParseException, MeilisearchException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date dateParsed = format.parse("2042-04-02T00:42:42Z");
        Key keyInfo = new Key();
        keyInfo.setDescription("Add documents: Products API key");
        keyInfo.setActions(new String[] {"*"});
        keyInfo.setIndexes(new String[] {"*"});
        keyInfo.setExpiresAt(dateParsed);
        Key key = client.createKey(keyInfo);
        System.out.println("生成的key是"+ key.getKey());
    }

}
