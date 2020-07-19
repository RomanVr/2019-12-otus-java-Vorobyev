package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DBServiceAccount implements DBService<Account> {
    private static Logger logger = LoggerFactory.getLogger(DBServiceAccount.class);

    private final Dao<Account> dao;

    public DBServiceAccount(Dao<Account> dao) {
        this.dao = dao;
    }

    @Override
    public long create(Account objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long id = dao.create(objectData);
                sessionManager.commitSession();
                logger.info("Create object by no: {}", id);
                return id;
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
                throw new DBServiceExeption(ex);
            }
        }
    }

    @Override
    public void update(Account objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                int countRow = dao.update(objectData);
                sessionManager.commitSession();
                logger.info("Object no = {} updated count row = {}", objectData.getNo(), countRow);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
                throw new DBServiceExeption(ex);
            }
        }
    }

    @Override
    public void createOrUpdate(Account objectData) {
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
    public Account load(long id, Class<Account> clazz) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> accountOptional = dao.load(id, clazz);
                logger.info("Account finned: {}", accountOptional.orElse(null));
                return accountOptional.orElse(null);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }
}
