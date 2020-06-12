package ru.otus.core.service;

public interface DBService<T> {
    public <T> long create(T objectData);

    public <T> void update(T objectData);

    public <T> void createOrUpdate(T objectData);

    public <T> T load(long id, Class<T> clazz);
}
