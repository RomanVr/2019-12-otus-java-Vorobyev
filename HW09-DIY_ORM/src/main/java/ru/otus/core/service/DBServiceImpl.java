package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.sessionmanager.SessionManager;

public class DBServiceImpl<T> implements DBService<T>{
    private static Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

    private final Dao<T> dao;

    public DBServiceImpl(Dao<T> dao) {
        this.dao = dao;
    }

    public <T> long create(T objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()){
            sessionManager.beginSession();
            try {
                long id = dao.create(objectData);
                sessionManager.commitSession();
                logger.info("Create object by id: {}", id);
                return id;
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
                throw new DBServiceExeption(ex);
            }
        }
    }

    @Override
    public void update(Object objectData) {

    }

    @Override
    public void createOrUpdate(Object objectData) {

    }

    @Override
    public Object load(long id, Class clazz) {
        return null;
    }
}
