package ru.otus.diyvisitor.types;

import ru.otus.diyvisitor.visitor.ProcessingField;
import ru.otus.diyvisitor.visitor.Service;

import java.lang.reflect.Field;

public class ProcessingArray extends ProcessingField {
    private final Object array;

    public ProcessingArray(Field field, Object array) {
        super(field);
        this.array = array;
    }

    @Override
    public void accept(Service service) {
        service.visit(this);
    }

    public Object getArray() {
        return array;
    }
}
