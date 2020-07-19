package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DBServiceUser implements DBService<User> {
    private static Logger logger = LoggerFactory.getLogger(DBServiceUser.class);

    private final Dao<User> dao;

    public DBServiceUser(Dao<User> dao) {
        this.dao = dao;
    }

    @Override
    public long create(User objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
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
    public void update(User objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                int countRow = dao.update(objectData);
                sessionManager.commitSession();
                logger.info("Object id = {} updated count row = {}", objectData.getId(), countRow);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
                throw new DBServiceExeption(ex);
            }
        }
    }

    @Override
    public void createOrUpdate(User objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.createOrUpdate(objectData);
                sessionManager.commitSession();
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
                throw new DBServiceExeption(ex);
            }
        }
    }

    @Override
    public User load(long id, Class<User> clazz) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = dao.load(id, clazz);
                logger.info("User finded: {}", userOptional.orElse(null));
                return userOptional.orElse(null);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }
}
