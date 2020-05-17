package ru.otus.diyvisitor.visitor;

import ru.otus.diyvisitor.types.*;

import javax.json.JsonObjectBuilder;

public interface Service {
    JsonObjectBuilder getJsonObj();

    void visit(ProcessingArray value);

    void visit(ProcessingObject value);

    void visit(ProcessingPrimitive value);

    void visit(ProcessingString value);

    void visit(ProcessingObjectInArray value);

    void visit(ProcessingObjectNull value);

    void visit(ProcessingArrayNull value);
}
