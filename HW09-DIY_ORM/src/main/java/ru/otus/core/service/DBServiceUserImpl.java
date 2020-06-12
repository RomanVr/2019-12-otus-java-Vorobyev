package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.UserTest;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class  DBServiceUserImpl implements DBServiceUser{
    private static Logger logger = LoggerFactory.getLogger(DBServiceUserImpl.class);

    private final UserDao userDao;

    public DBServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long saveUser(UserTest userTest) {
        try (SessionManager sessionManager = userDao.getSessionManager()){
            sessionManager.beginSession();
            try {
                long userId = userDao.saveUser(userTest);
                sessionManager.commitSession();

                logger.info("Create User by id: {}", userId);
                return userId;
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
                throw new DBServiceExeption(ex);
            }
        }
    }

    @Override
    public Optional<UserTest> getUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<UserTest> userOptional = userDao.findByid(id);
                logger.info("User finded: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
