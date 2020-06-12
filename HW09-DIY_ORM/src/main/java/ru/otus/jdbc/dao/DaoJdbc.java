package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

public class DaoJdbc<T> implements Dao<T> {
    private static final Logger logger = LoggerFactory.getLogger(DaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<T> jdbcMapper;

    public DaoJdbc(SessionManagerJdbc sessionManager, JdbcMapper<T> jdbcMapper) {
        this.sessionManager = sessionManager;
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public <T> long create(T objectData) {
        long id = jdbcMapper.create(objectData);
        return id;
    }

    @Override
    public <T> void update(T objectData) {

    }

    @Override
    public <T> void createOrUpdate(T objectData) {

    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
        return null;
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
