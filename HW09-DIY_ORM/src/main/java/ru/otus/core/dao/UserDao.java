package ru.otus.core.dao;

import ru.otus.core.model.UserTest;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<UserTest> findByid(long id);

    long saveUser(UserTest userTest);

    SessionManager getSessionManager();
}

