package com.sene.backend.service;

public interface ActiveDesactiveService<T,ID> {
    T ActiveDesactive(T entity, ID id);
}
