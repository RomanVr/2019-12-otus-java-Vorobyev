package ru.otus.diygson;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class DIYGson {
    private static String jsonNull = "null";

    private Set<Class> typesPrimitiveInt;
    private Set<Class> typesString;
    private Set<Class> typesDouble;

    public void types() {
        System.out.println("String : " + String.class);
        System.out.println("int : " + int.class);
        System.out.println("byte : " + byte.class);
        System.out.println("short : " + short.class);
        System.out.println("long : " + long.class);
        System.out.println("float : " + float.class);
        System.out.println("double : " + double.class);
        System.out.println("boolean : " + boolean.class);
        System.out.println("char : " + char.class);
        System.out.println();
        System.out.println("array : " + int[].class);
        System.out.println("Object : " +  Object.class);
        System.out.println("List : " + List.class.getTypeName());
        System.out.println("List : " + ArrayList.class.getTypeName());
        System.out.println("List instance : " + List.class.isAssignableFrom(ArrayList.class));
        System.out.println();
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

    private Object toObject(Object obj, Field field) {
        field.setAccessible(true);
        Object value = null;
        try {
            value = (Object) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    public DIYGson() {
        this.typesPrimitiveInt = new HashSet<>();
        typesPrimitiveInt.add(int.class);
        typesPrimitiveInt.add(byte.class);
        typesPrimitiveInt.add(short.class);

        this.typesString = new HashSet<>();
        typesString.add(String.class);
        typesString.add(char.class);

        this.typesDouble = new HashSet<>();
        typesDouble.add(float.class);
        typesDouble.add(double.class);
    }

    public String toJson(Object obj) {
        if (obj == null) return jsonNull;
        Class type = obj.getClass();
        if(isArray(type)) {
            JsonArrayBuilder jsArr = Json.createArrayBuilder();
            toJsonArrayIter(jsArr, obj);
            return jsArr.build().toString();
        }

        if (List.class.isAssignableFrom(type)) {
            JsonArrayBuilder jsArr = Json.createArrayBuilder();
            List newList = (List) obj;
            System.out.println("Size list : " + newList.size());
            Class typeElemList = newList.get(0).getClass();
            System.out.println("Type elem List : " + typeElemList.getTypeName());
            int[] newArr = new int[newList.size()];
            toJsonArrayIter(jsArr, newArr);
            return jsArr.build().toString();
        }

        if(isObject(type)) {
            JsonObjectBuilder jsObj = Json.createObjectBuilder();
            toJsonObjectIter(jsObj, obj);
            return jsObj.build().toString();
        }
        return "";
    }

    private void toJsonObjectIter(JsonObjectBuilder jsObj, Object obj) {
        System.out.println("Object : " + obj.getClass());
        Field[] fields = obj.getClass().getDeclaredFields();

        for(Field field: Arrays.asList(fields)) {
            Class fieldType = field.getType();
            if (this.typesString.contains(fieldType)) {
                String value = toString(obj, field);
                jsObj.add(field.getName(), value);
                continue;
            }
            if (this.typesPrimitiveInt.contains(fieldType)) {
                int value = toInt(obj, field);
                jsObj.add(field.getName(), value);
                continue;
            }
            if (this.typesDouble.contains(fieldType)) {
                double value = toDouble(obj, field);
                jsObj.add(field.getName(), value);
                continue;
            }
            if (fieldType.getTypeName().equals("long")) {
                long value = toLong(obj, field);
                jsObj.add(field.getName(), value);
                continue;
            }
            if (fieldType.getTypeName().equals("boolean")) {
                boolean value = toBoolean(obj, field);
                jsObj.add(field.getName(), value);
                continue;
            }
            if (isArray(fieldType)) {
                if (isNull(obj, field)) continue;
                JsonArrayBuilder jsArrNew = Json.createArrayBuilder();
                Object value = toObject(obj, field);
                toJsonArrayIter(jsArrNew, value);
                jsObj.add( field.getName(), jsArrNew);
                continue;
            }
            if (List.class.isAssignableFrom(fieldType)) {
                if (isNull(obj, field)) continue;
                JsonArrayBuilder jsArrNew = Json.createArrayBuilder();
                Object value = toObject(obj, field);
                List newValue = (List) value;
                toJsonArrayIter(jsArrNew, newValue.toArray());
                jsObj.add( field.getName(), jsArrNew);
                continue;
            }
            if (isObject(fieldType)) {
                if (isNull(obj, field)) continue;
                JsonObjectBuilder jsObjNew = Json.createObjectBuilder();
                Object value = toObject(obj, field);
                toJsonObjectIter(jsObjNew, value);
                jsObj.add( field.getName(), jsObjNew);
            }
        }
    }


    private void toJsonArrayIter(JsonArrayBuilder jsArr, Object arr) {
        System.out.println("Object : " + arr.getClass().getTypeName() + "   Class : " + arr.getClass());

        String typeName = arr.getClass().getTypeName();

        if (typeName.equals("int[]") || typeName.equals("byte[]") || typeName.equals("short[]")) {
            int[] arrCast = (int[]) arr;
            for(int i = 0; i < arrCast.length; i += 1) {
                jsArr.add(arrCast[i]);
            }
        } else if (typeName.equals("long[]")) {

        } else if (typeName.equals("java.lang.String[]") || typeName.equals("char[]")) {
            System.out.println("Это массив строк");
            String[] arrCast = (String[]) arr;
            for(int i = 0; i < arrCast.length; i += 1) {
                jsArr.add(arrCast[i]);
            }
        } else if (typeName.equals("float[]")) {
            float[] arrCast = (float[]) arr;
            for(int i = 0; i < arrCast.length; i += 1) {
                jsArr.add(arrCast[i]);
            }
        } else if (typeName.equals("double[]")) {
            double[] arrCast = (double[]) arr;
            for(int i = 0; i < arrCast.length; i += 1) {
                jsArr.add(arrCast[i]);
            }
        } else if (List.class.isAssignableFrom(arr.getClass())) {

        } else if (arr.getClass().toString().contains("class")) {
            System.out.println("Это массив объектов");
            Object[] arrCast = (Object[]) arr;
            for(int i = 0; i < arrCast.length; i += 1) {
                JsonObjectBuilder jsobj = Json.createObjectBuilder();
                toJsonObjectIter(jsobj, arrCast[i]);
                jsArr.add(jsobj);
            }
        }
    }

    private boolean isNull(Object obj, Field field) {
        field.setAccessible(true);
        Object value = null;
        try {
            value = (Object) field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value == null;
    }

    private boolean isObject(Class type) {
        System.out.println("isObject " + type.toString() + " : " + type.toString().contains("class"));
        return type.toString().contains("class");
    }

    private boolean isArray(Class type) {
        System.out.println("isArray " + type.toString() + " : " + type.toString().contains("["));
        return type.toString().contains("[");
    }
}
