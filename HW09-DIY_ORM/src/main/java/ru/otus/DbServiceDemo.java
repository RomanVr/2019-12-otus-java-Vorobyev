package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
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
        DBExecutor<User> dbExecutor = new DBExecutor<>();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);

        DBServiceUser  dbServiceUser = new DBServiceUserImpl(userDao);
        long id = dbServiceUser.saveUser(new User(0, "userName"));
        Optional<User> user = dbServiceUser.getUser(id);

        System.out.println(user);
        user.ifPresentOrElse(
                cruser -> logger.info("created user, name:{}", cruser.getName()),
                () -> logger.info("user was not created")
        );
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(
                 "create table user(id long auto_increment, name varchar(50))")) {
            pst.executeUpdate();
        }
        logger.info("Table created");
    }
}
