package com.ducheng.easy.ms.service;


import com.meilisearch.sdk.SearchRequest;
import java.util.List;

interface DocumentOperations<T> {

    T get(String identifier) throws InstantiationException, IllegalAccessException;

    List<T> list();

    List<T> list(int limit);

    List<T> list(int offset, int limit);

    long add(T document);

    long update(T document);

    long add(List<T> documents);

    long update(List<T> documents);

    long delete(String identifier);

    long deleteBatch(String... documentsIdentifiers);

    long deleteAll();

    List<T> query(String query);

    List<T> query(SearchRequest searchRequest);
}
