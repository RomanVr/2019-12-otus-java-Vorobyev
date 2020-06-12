package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.UserTest;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DBServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.dao.DBExecutor;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        DbServiceDemo demo = new DbServiceDemo();

        demo.createTable(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DBExecutor<UserTest> dbExecutor = new DBExecutor<>();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);

        DBServiceUser  dbServiceUser = new DBServiceUserImpl(userDao);
        long id = dbServiceUser.saveUser(new UserTest(0, "userName"));
        Optional<UserTest> userTest = dbServiceUser.getUser(id);

        System.out.println(userTest);
        userTest.ifPresentOrElse(
                cruser -> logger.info("created user, name:{}", cruser.getName()),
                () -> logger.info("user was not created")
        );
    }

    private void createTableUser(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement(
                "create table user(id bigint(20) NOT NULL auto_increment," +
                        "name varchar(255)," +
                        "age int(3)")) {
            pst.executeUpdate();
        }
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(
                 "create table userTest(id long auto_increment, name varchar(50))")) {
            pst.executeUpdate();
        }
        logger.info("Table created");
    }
}
