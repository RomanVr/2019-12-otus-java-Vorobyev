package ru.otus.core.model;

public class UserTest {
    private final long id;
    private final String name;

    public UserTest(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UserTest {" +
                "id=" + id +
                ", name='" + name + '\'' +
                "}";
    }
}
