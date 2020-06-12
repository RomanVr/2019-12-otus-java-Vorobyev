package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.jdbc.Id;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapper.class);

    private final SessionManagerJdbc sessionManager;
    private final DBExecutorMapper<T> dbExecutor;

    public JdbcMapper(SessionManagerJdbc sessionManager, DBExecutorMapper<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    public <T> long create(T objectData) {
        logger.info("Object : {}" , objectData);

        String tableName = objectData.getClass().getSimpleName(); //From Reflection
        logger.info("Class name : {}", tableName);

        List<String> fieldNames = new ArrayList<>(); //From Reflection
        List<String> fieldValues = new ArrayList();
        Field[] fields = objectData.getClass().getDeclaredFields();
        for (Field field: fields){
            field.setAccessible(true);
            try {
                logger.info("name field : {} value : {} ", field.getName(), field.get(objectData));
                logger.info("Is anno Id : {}", field.isAnnotationPresent(Id.class));
                if (!field.isAnnotationPresent(Id.class)) {
                    fieldNames.add(field.getName());
                    fieldValues.add(field.get(objectData).toString());
                }
            } catch (IllegalAccessException ex) {
                logger.error(ex.getLocalizedMessage(), ex);
            }
        }

        Annotation[] annotations = objectData.getClass().getAnnotations();
        logger.info("Anno Class size : {}", annotations.length );
        for (Annotation annotation: annotations) {
            logger.info("Anno : {}", annotations);
        }

        String sqlString = getSqlStringInsert(tableName, fieldNames);
        logger.info("Sql string : {}", sqlString);

        try {
            long id = dbExecutor.insertRecord(getConnection(), sqlString, fieldValues);
            return id;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new UserDaoException(ex);
        }
    }

    private String getSqlStringInsert(String tableName, List<String> nameFields) {
        StringBuilder sqlString = new StringBuilder("");

        sqlString.append("insert into ");
        sqlString.append(tableName);
        sqlString.append("(");
        for(int i = 0; i < nameFields.size(); i += 1) {
            if (i != nameFields.size() - 1) {
                sqlString.append(nameFields.get(i));
                sqlString.append(", ");
            } else {
                sqlString.append(nameFields.get(i));
            }
        }
        sqlString.append(") values(");
        for(int i = 0; i < nameFields.size(); i += 1) {
            if (i != nameFields.size() - 1) {
                sqlString.append("?, ");
            } else {
                sqlString.append("?");
            }
        }
        sqlString.append(")");
        return sqlString.toString();
    }

    public <T> void update(T objectData) {

    }

    public <T> void createOrUpdate(T objectdata) {

    }

    public <T> T load(long id, Class<T> clazz) {
        return null;
    }
}
