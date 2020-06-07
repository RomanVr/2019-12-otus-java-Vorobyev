package ru.otus.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class H2demo {
    private static final String URL = "jdbc:h2:mem:";
    private static Logger logger = LoggerFactory.getLogger(H2demo.class);
    private final Connection connection;

    public H2demo() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        this.connection.setAutoCommit(false);
    }

    public void createTable() throws SQLException {
        try(PreparedStatement pst = connection.prepareStatement(
                "create table test(id int, name varchar(50))"
        )) {
            pst.executeUpdate();
        }
    }

    public void insertRecord(int id, String name) throws SQLException {
        try(PreparedStatement pst = connection.prepareStatement(
                "insert into test(id, name) values (? , ?)"
        )) {
            Savepoint savepoint = connection.setSavepoint("savePoint");
            pst.setInt(1, id);
            pst.setString(2, name);
            try {
                int rowCount = pst.executeUpdate();
                connection.commit();
                logger.info("inserted rowCount: {}", rowCount);
            } catch (SQLException ex) {
                this.connection.rollback(savepoint);
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    public void selectRecord(int id) throws SQLException {
        try(PreparedStatement pst = connection.prepareStatement(
            "select name from test where id = ?"
        )) {
            pst.setInt(1, id);
            try(ResultSet rs = pst.executeQuery()) {
                StringBuilder outString = new StringBuilder();
                outString.append("name: ");
                if (rs.next()) {
                    outString.append(rs.getString("name"));
                }
                logger.info(outString.toString());
            }
        }
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}
