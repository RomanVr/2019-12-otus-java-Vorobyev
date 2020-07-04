package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class DaoJdbc<T> implements Dao<T> {
    private static final Logger logger = LoggerFactory.getLogger(DaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DaoJdbcMapper<T> daoJdbcMapper;

    public DaoJdbc(SessionManagerJdbc sessionManager, DaoJdbcMapper<T> daoJdbcMapper) {
        this.sessionManager = sessionManager;
        this.daoJdbcMapper = daoJdbcMapper;
    }

    @Override
    public long create(T objectData) {
        long id = daoJdbcMapper.create(objectData);
        return id;
    }

    @Override
    public int update(T objectData) {
        return daoJdbcMapper.update(objectData);
    }

    @Override
    public void createOrUpdate(T objectData) {
        daoJdbcMapper.createOrUpdate(objectData);
    }

    @Override
    public Optional<T> load(long id, Class<T> clazz) {
        return daoJdbcMapper.load(id, clazz);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
