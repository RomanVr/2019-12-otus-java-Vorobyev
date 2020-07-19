package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.Dao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.dao.DBExecutorMapper;
import ru.otus.jdbc.dao.DaoJdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DbServiceMapperDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceMapperDemo.class);

    public static void main(String[] args) {
        DataSource dataSource = new DataSourceH2();
        DbServiceMapperDemo demo = new DbServiceMapperDemo();

        demo.createTableUser(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

        DBExecutorMapper<User> dbExecutorMapper = new DBExecutorMapper<User>();
        Dao<User> dao = new DaoJdbcMapper<User>(sessionManager, dbExecutorMapper);

        DBServiceUser dbService = new DBServiceUser(dao);
        long id = dbService.create(new User(0, "name1", 24));
        dbService.update(new User(id, "name2", 33));
        User userFromDB = dbService.load(id, User.class);

        dbService.createOrUpdate(new User(1, "name3", 33));

        userFromDB = dbService.load(1, User.class);

    }

    private void createTableUser(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(
                     "create table User(id long(20) NOT NULL auto_increment, " +
                             "name varchar(255), " +
                             "age int(3))")) {
            pst.executeUpdate();
            logger.info("Table User created");
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
