package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class DBExecutorMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(DBExecutor.class);

    public long insertRecord(Connection connection, String sql, List<String> params) throws SQLException {
        Savepoint savepoint = connection.setSavepoint("sp");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx += 1) {
                pst.setString(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            connection.commit();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            connection.rollback(savepoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

}
