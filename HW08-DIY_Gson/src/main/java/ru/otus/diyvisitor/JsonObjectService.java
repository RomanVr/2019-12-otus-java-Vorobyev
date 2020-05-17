package ru.otus.diyvisitor;

import ru.otus.diyvisitor.types.*;
import ru.otus.diyvisitor.visitor.Service;

import javax.json.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class JsonObjectService implements Service {
    private JsonObjectBuilder jsonObj;

    @Override
    public JsonObjectBuilder getJsonObj() {
        return jsonObj;
    }

    private Set<Class> typesPrimitiveInt;

    public JsonObjectService() {
        this.jsonObj = Json.createObjectBuilder();
        init();
    }

    public JsonObjectService(JsonObjectBuilder jsonObjectBuilder) {
        this.jsonObj = jsonObjectBuilder;
        init();
    }

    private void init() {
        this.typesPrimitiveInt = new HashSet<>();
        typesPrimitiveInt.add(int.class);
        typesPrimitiveInt.add(byte.class);
        typesPrimitiveInt.add(short.class);
    }

    @Override
    public void visit(ProcessingArray tArray) {
        Object array = tArray.getArray();
        String typeName = array.getClass().getTypeName();

        Field field = tArray.getField();
        JsonArrayBuilder jsArray = Json.createArrayBuilder();

        switch (typeName) {
            case "int[]":
            case "byte[]":
            case "short[]": {
                int[] arrCast = (int[]) array;
                for (int elem : arrCast) {
                    jsArray.add(elem);
                }
                break;
            }
            case "long[]": {
                long[] arrCast = (long[]) array;
                for (long elem : arrCast) {
                    jsArray.add(elem);
                }
                break;
            }
            case "java.lang.String[]":
            case "char[]": {
                String[] arrCast = (String[]) array;
                for (String elem : arrCast) {
                    jsArray.add(elem);
                }
                break;
            }
            case "float[]": {
                float[] arrCast = (float[]) array;
                for (float elem : arrCast) {
                    jsArray.add(elem);
                }
                break;
            }
            case "double[]": {
                double[] arrCast = (double[]) array;
                for (double elem : arrCast) {
                    jsArray.add(elem);
                }
                break;
            }
        }

        this.jsonObj.add(field.getName(), jsArray);
    }

    @Override
    public void visit(ProcessingObject obj) {
        JsonObjectBuilder jsonObjectBuilderNew = obj.getJsonObjectBuilder();
        this.jsonObj.add(obj.getField().getName(), jsonObjectBuilderNew);
    }

    @Override
    public void visit(ProcessingObjectNull obj) {
        this.jsonObj.add(obj.getField().getName(), JsonValue.EMPTY_JSON_OBJECT);
    }

    @Override
    public void visit(ProcessingArrayNull array) {
        this.jsonObj.add(array.getField().getName(), JsonValue.EMPTY_JSON_ARRAY);
    }

    @Override
    public void visit(ProcessingObjectInArray obj) {
        JsonArrayBuilder jsonArrayBuilder = obj.getJsonArrayBuilder();
        this.jsonObj.add(obj.getField().getName(), jsonArrayBuilder);
    }

    @Override
    public void visit(ProcessingPrimitive primitive) {
        Class<?> fieldType = primitive.getField().getType();
        if (this.typesPrimitiveInt.contains(fieldType)) {
            int value = toInt(primitive.getObj(), primitive.getField());
            this.jsonObj.add(primitive.getField().getName(), value);
        } else if(fieldType.getTypeName().equals("boolean")) {
            boolean value = toBoolean(primitive.getObj(), primitive.getField());
            this.jsonObj.add(primitive.getField().getName(), value);
        } else if(fieldType.getTypeName().equals("long")) {
            long value = toLong(primitive.getObj(), primitive.getField());
            this.jsonObj.add(primitive.getField().getName(), value);
        } else if(fieldType.getTypeName().equals("char")) {
            String value = toString(primitive.getObj(), primitive.getField());
            this.jsonObj.add(primitive.getField().getName(), value);
        } else if(fieldType.getTypeName().equals("double")) {
            double value = toDouble(primitive.getObj(), primitive.getField());
            this.jsonObj.add(primitive.getField().getName(), value);
        } else if(fieldType.getTypeName().equals("float")) {
            double value = toFloat(primitive.getObj(), primitive.getField());
            this.jsonObj.add(primitive.getField().getName(), value);
        }
    }

    @Override
    public void visit(ProcessingString str) {
        String value = toString(str.getObj(), str.getField());
        if (value != null) {
            this.jsonObj.add(str.getField().getName(), value);
        }
    }

    private int toInt(Object obj, Field field) {
        field.setAccessible(true);
        int value = 0;
        try {
            value = (int) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private String toString(Object obj, Field field) {
        field.setAccessible(true);
        String value = "";
        try {
            value = (String) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private double toDouble(Object obj, Field field) {
        field.setAccessible(true);
        double value = 0;
        try {
            value = (double) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private double toFloat(Object obj, Field field) {
        field.setAccessible(true);
        float value = 0;
        try {
            value = (float) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private long toLong(Object obj, Field field) {
        field.setAccessible(true);
        long value = 0;
        try {
            value = (long) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private boolean toBoolean(Object obj, Field field) {
        field.setAccessible(true);
        boolean value = false;
        try {
            value = (boolean) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    public String build() {
        return this.jsonObj.build().toString();
    }
}
