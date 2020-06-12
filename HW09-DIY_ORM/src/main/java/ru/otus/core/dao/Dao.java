package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;

public interface Dao<T> {
    public <T> long create(T objectData);

    public <T> void update(T objectData);

    public <T> void createOrUpdate(T objectData);

    public <T> T load(long id, Class<T> clazz);

    public SessionManager getSessionManager();
}
