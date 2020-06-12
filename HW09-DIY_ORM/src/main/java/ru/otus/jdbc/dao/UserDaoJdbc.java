package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.UserTest;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DBExecutor<UserTest> dbExecutor;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DBExecutor<UserTest> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    @Override
    public long saveUser(UserTest userTest) {
        try {
            return dbExecutor.insertRecord(getConnection(), "insert into userTest(name) values(?)", Collections.singletonList(userTest.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public Optional<UserTest> findByid(long id) {
        try {
            return dbExecutor.selectRecord(
                    getConnection(),
                    "select id, name from userTest where id = ?",
                    id,
                    resultSet -> {
                        try {
                            if (resultSet.next()) {
                                return new UserTest(resultSet.getLong("id"), resultSet.getString("name"));
                            }
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    }
            );
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return Optional.empty();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
