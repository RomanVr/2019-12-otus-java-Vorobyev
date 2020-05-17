package ru.otus.diyvisitor.types;

import ru.otus.diyvisitor.visitor.ProcessingField;
import ru.otus.diyvisitor.visitor.Service;

import java.lang.reflect.Field;

public class ProcessingObjectNull extends ProcessingField {
    public ProcessingObjectNull(Field field) {
        super(field);
    }

    @Override
    public void accept(Service service) {
        service.visit(this);
    }
}
