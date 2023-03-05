package com.ducheng.easy.ms.service;

import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.Settings;
import com.meilisearch.sdk.UpdateStatus;
import com.ducheng.easy.ms.json.SearchResult;


import java.util.List;


interface DocumentOperations<T> {

    T get(String identifier);

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

    SearchResult<T> search(String q);

    SearchResult<T> search(String q, int offset, int limit);

    SearchResult<T> search(SearchRequest sr);

    Settings getSettings();

    UpdateStatus updateSettings(Settings settings);

    UpdateStatus resetSettings();

    UpdateStatus getUpdate(int updateId);

    UpdateStatus[] getUpdates();
}
