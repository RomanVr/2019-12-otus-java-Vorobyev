package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface Dao<T> {
    public long create(T objectData);

    public int update(T objectData);

    public void createOrUpdate(T objectData);

    public Optional<T> load(long id, Class<T> clazz);

    public SessionManager getSessionManager();
}
