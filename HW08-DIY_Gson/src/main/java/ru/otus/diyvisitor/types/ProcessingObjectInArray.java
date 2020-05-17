package ru.otus.diyvisitor.types;

import ru.otus.diyvisitor.visitor.ProcessingField;
import ru.otus.diyvisitor.visitor.Service;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;

public class ProcessingObjectInArray extends ProcessingField {
    private final JsonArrayBuilder jsonArrayBuilder;

    public JsonArrayBuilder getJsonArrayBuilder() {
        return jsonArrayBuilder;
    }

    public ProcessingObjectInArray(Field field, JsonArrayBuilder jsonArrayBuilder) {
        super(field);
        this.jsonArrayBuilder = jsonArrayBuilder;
    }

    @Override
    public void accept(Service service) {
        service.visit(this);
    }
}
