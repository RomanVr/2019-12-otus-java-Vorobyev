package ru.otus.diyvisitor.types;

import ru.otus.diyvisitor.visitor.ProcessingField;
import ru.otus.diyvisitor.visitor.ProcessingType;
import ru.otus.diyvisitor.visitor.Service;

import java.lang.reflect.Field;

public class ProcessingString extends ProcessingField {
    public final Object obj;

    public Object getObj() {
        return obj;
    }

    public ProcessingString(Field field, Object obj) {
        super(field);
        this.obj = obj;
    }

    @Override
    public void accept(Service service) {
        service.visit(this);
    }
}
