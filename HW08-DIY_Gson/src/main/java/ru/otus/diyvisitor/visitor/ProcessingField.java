package ru.otus.diyvisitor.visitor;

import java.lang.reflect.Field;

public abstract class ProcessingField implements ProcessingType{
    private final Field field;

    protected  ProcessingField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public String getName() {
        return field == null ? "null" : field.getName();
    }}
