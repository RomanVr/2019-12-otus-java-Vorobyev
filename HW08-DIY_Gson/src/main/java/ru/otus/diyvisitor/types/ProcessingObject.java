package ru.otus.diyvisitor.types;

import ru.otus.diyvisitor.visitor.ProcessingField;
import ru.otus.diyvisitor.visitor.Service;

import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;

public class ProcessingObject extends ProcessingField {
    private final JsonObjectBuilder jsonObjectBuilder;

    public JsonObjectBuilder getJsonObjectBuilder() {
        return this.jsonObjectBuilder;
    }

    public ProcessingObject(Field field, JsonObjectBuilder jsonObjectBuilder) {
        super(field);
        this.jsonObjectBuilder = jsonObjectBuilder;
    }

    @Override
    public void accept(Service service) {
        service.visit(this);
    }
}
