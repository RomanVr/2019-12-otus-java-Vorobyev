package ru.otus.core.service;

public interface DBService<T> {
    public long create(T objectData);

    public void update(T objectData);

    public void createOrUpdate(T objectData);

    public T load(long id, Class<T> clazz);
}
