package ru.otus.diyvisitor.types;

import ru.otus.diyvisitor.visitor.ProcessingField;
import ru.otus.diyvisitor.visitor.Service;

import java.lang.reflect.Field;

public class ProcessingPrimitive extends ProcessingField {
    private final Object obj;

    public Object getObj() {
        return obj;
    }

    public ProcessingPrimitive(Field field, Object obj) {
        super(field);
        this.obj = obj;
    }

    @Override
    public void accept(Service service) {
        service.visit(this);
    }
}
