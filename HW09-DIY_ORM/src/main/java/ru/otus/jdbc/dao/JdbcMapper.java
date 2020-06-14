package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.service.DBServiceExeption;
import ru.otus.jdbc.Id;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

    public long create(T objectData) {
        logger.info("Object to insert: {}" , objectData);
        Field[] fields = objectData.getClass().getDeclaredFields();

        //Проверка есть ли аннотация @Id в классе
        if (!isExistAnnoId(fields)) {
            throw new UserDaoException("This class is not Entity!");
        }

        String tableName = objectData.getClass().getSimpleName();
        List<String> fieldNames = new ArrayList<>();
        List<String> fieldValues = new ArrayList();

        for (Field field: fields){
            field.setAccessible(true);
            try {
                if (!field.isAnnotationPresent(Id.class)) {
                    fieldNames.add(field.getName());
                    fieldValues.add(field.get(objectData).toString());
                }
            } catch (IllegalAccessException ex) {
                logger.error(ex.getLocalizedMessage(), ex);
            }
        }

        String sqlString = getSqlStringInsert(tableName, fieldNames);
        logger.info("Sql string insert: {}", sqlString);

        try {
            long id = dbExecutor.insertRecord(getConnection(), sqlString, fieldValues);
            return id;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new UserDaoException(ex);
        }
    }

    public int update(T objectData) {
        logger.info("Object to update: {}" , objectData);
        Field[] fields = objectData.getClass().getDeclaredFields();
        //Проверка есть ли аннотация @Id в классе
        if (!isExistAnnoId(fields)) {
            throw new UserDaoException("This class is not Entity!");
        }

        String tableName = objectData.getClass().getSimpleName();
        List<String> fieldNames = new ArrayList<>();
        List<String> fieldValues = new ArrayList();
        String fieldIdName = "";
        String fieldIdValue = "";

        for (Field field: fields){
            field.setAccessible(true);
            try {
                if (!field.isAnnotationPresent(Id.class)) {
                    fieldNames.add(field.getName());
                    fieldValues.add(field.get(objectData).toString());
                } else {
                    fieldIdName = field.getName();
                    fieldIdValue = field.get(objectData).toString();
                }
            } catch (IllegalAccessException ex) {
                logger.error(ex.getLocalizedMessage(), ex);
            }
        }

        fieldNames.add(fieldIdName);
        fieldValues.add(fieldIdValue);

        String sqlString = getSqlStringUpdate(tableName, fieldNames);
        logger.info("Sql string update: {}", sqlString);

        try {
            return dbExecutor.updateRecord(getConnection(), sqlString, fieldValues);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new UserDaoException(ex);
        }
    }

    public void createOrUpdate(T objectdata) {
        logger.info("object createOrUpdate : {}", objectdata.getClass().getSimpleName());
        Field[] fields = objectdata.getClass().getDeclaredFields();
        //Проверка есть ли аннотация @Id в классе
        if (!isExistAnnoId(fields)) {
            throw new UserDaoException("This class is not Entity!");
        }

        long id = getValueIdAnnotation(objectdata);
        logger.info("Id for update  - {}", id);
        if (id > 0) {//Получаем объект из DB
            Optional<T> optionalObj = load(id, (Class<T>) objectdata.getClass());
            T objectDB = optionalObj.orElse(null);
            if (objectDB != null) {//есть в базе объект для обновления
                logger.info("Object from DB : {}", objectDB.toString());
                if (objectdata.equals(objectDB)){
                    logger.info("Objects is equals");
                } else {
                    logger.info("Objects not equals!");
                    int count = update(objectdata);
                    logger.info("Object updated, count row : {}", count);
                }
                return;
            } else {
                logger.info("Not exist object in DB!");
            }
        }
        // Создаем новый объект
        create(objectdata);
    }

    private long getValueIdAnnotation(final T objectdata) {
        Field[] fields = objectdata.getClass().getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    return (long) field.get(objectdata);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public Optional<T> load(long id, Class<T> clazz) {
        logger.info("Object to load id = {} clazz : {}" , id, clazz);
        Field[] fields = clazz.getDeclaredFields();
        //Проверка есть ли аннотация @Id в классе
        if (!isExistAnnoId(fields)) {
            throw new UserDaoException("This class is not Entity!");
        }

        String tableName = clazz.getSimpleName();
        List<String> fieldNames = new ArrayList<>();
        Class<?>[] paramTypes = new Class<?>[fields.length];

        for (int i = 0; i < fields.length; i += 1){
            fieldNames.add(fields[i].getName());
            paramTypes[i] = fields[i].getType();
        }

        Constructor<T> constructor = null;
        T newObject = null;
        try {
            constructor = clazz.getConstructor(paramTypes);
        } catch (NoSuchMethodException ex) {
            throw new DBServiceExeption(ex);
        }

        String sqlString = getSqlStringSelect(tableName, fieldNames);
        logger.info("Sql string select: {}", sqlString);

        Constructor<T> finalConstructor = constructor;

        Function<ResultSet, T> rsHandler = resultSet -> {
            try {
                if (resultSet.next()) {
                    int columnCount = resultSet.getMetaData().getColumnCount();
                    ResultSetMetaData rsMeta = resultSet.getMetaData();
                    List args = new ArrayList();
                    for (int i = 1; i <= columnCount; i += 1) {
                        String columnType = rsMeta.getColumnTypeName(i);
                        switch (columnType) {
                            case "BIGINT":
                                args.add(resultSet.getLong(i));
                                break;
                            case "VARCHAR":
                                args.add(resultSet.getString(i));
                                break;
                            case "INTEGER":
                                args.add(resultSet.getInt(i));
                                break;
                        }
                    }
                    try {
                        T objectFromDb = finalConstructor.newInstance(args.toArray());
                        return objectFromDb;
                    } catch(InvocationTargetException | IllegalAccessException | InstantiationException ex) {
                        throw new DBServiceExeption(ex);
                    }
                }
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
            }
            return null;
        };

        try {
            return dbExecutor.selectRecord(getConnection(), sqlString, id, rsHandler);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return Optional.empty();
    }
    //"select id, name from userTest where id = ?"
    private String getSqlStringSelect(String tableName, List<String> fieldNames) {
        StringBuilder sqlString = new StringBuilder("");

        sqlString.append("SELECT ");
        for(int i = 0; i < fieldNames.size(); i += 1) {
            sqlString.append(fieldNames.get(i));
            if (i != fieldNames.size() - 1) {
                sqlString.append(", ");
            }
        }
        sqlString.append(" FROM ");
        sqlString.append(tableName);
        sqlString.append(" WHERE ");
        sqlString.append(fieldNames.get(0));
        sqlString.append(" = ?");
        return sqlString.toString();
    }

    //UPDATE CUSTOMERS SET ADDRESS = 'Pune', SALARY = 1000 WHERE ID = 6;
    private String getSqlStringUpdate(String tableName, List<String> fieldNames) {
        StringBuilder sqlString = new StringBuilder("");

        sqlString.append("UPDATE ");
        sqlString.append(tableName);
        sqlString.append(" SET ");
        for(int i = 0; i < fieldNames.size() - 1; i += 1) {
            sqlString.append(fieldNames.get(i));
            if (i != fieldNames.size() - 2) {
                sqlString.append(" = ?, ");
            } else {
                sqlString.append(" = ? ");
            }
        }
        sqlString.append("WHERE ");
        sqlString.append(fieldNames.get(fieldNames.size() - 1));
        sqlString.append(" = ?");
        return sqlString.toString();
    }

    private boolean isExistAnnoId(Field[] fields) {
        boolean existAnnoId = false;

        for (Field field: fields) {
            if (field.isAnnotationPresent(Id.class)){
                existAnnoId = true;
            }
        }
        return existAnnoId;
    }

    private String getSqlStringInsert(String tableName, List<String> fieldNames) {
        StringBuilder sqlString = new StringBuilder("");

        sqlString.append("insert into ");
        sqlString.append(tableName);
        sqlString.append("(");
        for(int i = 0; i < fieldNames.size(); i += 1) {
            if (i != fieldNames.size() - 1) {
                sqlString.append(fieldNames.get(i));
                sqlString.append(", ");
            } else {
                sqlString.append(fieldNames.get(i));
            }
        }
        sqlString.append(") values(");
        for(int i = 0; i < fieldNames.size(); i += 1) {
            if (i != fieldNames.size() - 1) {
                sqlString.append("?, ");
            } else {
                sqlString.append("?");
            }
        }
        sqlString.append(")");
        return sqlString.toString();
    }
}
