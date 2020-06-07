package ru.otus.jdbc.sessionmanager;

import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.core.sessionmanager.SessionManagerExeption;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionManagerJdbc implements SessionManager {

    private static final int TIMEOUT_IN_SECOND = 5;
    private final DataSource dataSource;
    private Connection connection;
    private DatabaseSessionJdbc databaseSession;

    public SessionManagerJdbc(DataSource dataSource) {
        if (dataSource == null) {
            throw new SessionManagerExeption("Datasource is null!!!");
        }
        this.dataSource = dataSource;
    }

    @Override
    public void beginSession() {
        try {
            connection = dataSource.getConnection();
            databaseSession = new DatabaseSessionJdbc(connection);
        } catch (SQLException ex) {
            throw new SessionManagerExeption(ex);
        }
    }

    @Override
    public void commitSession() {
        checkConnection();
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new SessionManagerExeption(ex);
        }
    }

    @Override
    public void rollbackSession() {
        checkConnection();
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new SessionManagerExeption(ex);
        }
    }

    @Override
    public DatabaseSessionJdbc getCurrentSession() {
        checkConnection();
        return databaseSession;
    }

    @Override
    public void close() {
        checkConnection();
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new SessionManagerExeption(ex);
        }
    }

    private void checkConnection() {
        try {
            if (connection == null || !connection.isValid(TIMEOUT_IN_SECOND)) {
                throw new SessionManagerExeption("Connection is invalid!!!");
            }
        } catch (SQLException ex) {
            throw new SessionManagerExeption(ex);
        }
    }
}
