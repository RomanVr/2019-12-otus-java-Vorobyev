package ru.otus.core.service;

import ru.otus.core.model.UserTest;

import java.util.Optional;

public interface DBServiceUser {
    long saveUser(UserTest userTest);

    Optional<UserTest> getUser(long id);
}
