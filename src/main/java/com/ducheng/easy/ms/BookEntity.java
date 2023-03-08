package com.ducheng.easy.ms;

import com.ducheng.easy.ms.anotation.CustomIndex;

import java.io.Serializable;

@CustomIndex
public class BookEntity implements Serializable {

    private String bookName;

    private String id;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "bookName='" + bookName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
