package com.ducheng.easy.ms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = MeiLiSearchApplication.class)
public class MeiLiSearchTest {

    @Autowired
    private BookEntityRepository bookEntityRepository;

    @Test
    public void addEntity() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookName("语文");
        bookEntity.setId("1");
        long result = bookEntityRepository.add(bookEntity);
        System.out.println("添加书籍返回结果:" + result);
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
    }

    @Test
    public void get() {
        BookEntity bookEntity = bookEntityRepository.get("8");
        System.out.println("返回的数据是:" + bookEntity.toString());
    }
}
