package ru.otus.diyvisitor;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DIYGson {

    private Set<Class> typesPrimitiveInt;
    private Set<Class> typesPrimitive;
    private static final String[] REPLACEMENT_CHARS = new String[128];

    public DIYGson() {
        this.typesPrimitiveInt = new HashSet<>();
        this.typesPrimitive = new HashSet<>();
        init();
    }

    public String toJson(Object testObj) {
        if (testObj == null) return "null";

        System.out.println("Class : " + testObj.getClass().getSimpleName());
        // если примитивный объект
        if (typesPrimitive.contains(testObj.getClass())) {
            return toJsonPrimitive(testObj);
        }

        if (testObj instanceof String) {// если строка
            System.out.println("String testObj");
            String str = toJsonString(testObj.toString());
            return str;
        }

        //если массив примитивов
        if (testObj.getClass().isArray()) {
            System.out.println("Array testObj");
            return toJsonArray(testObj);
        }
        //если List
        if (testObj instanceof List) {
            System.out.println("List testObj");
            List list = (List) testObj;
            Object[] array = list.toArray();
            return toJsonList(array);
        }

        System.out.println("Object testObj");

        JsonObjectService jsonService = new JsonObjectService();
        try {
            new TraversalObject().toJson(testObj, jsonService);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonService.build();
    }

    private String toJsonString(String str) {
        int size = str.length();
        String replacement = "";
        String newStr = "\"";
        for (int i = 0; i < size; i += 1) {
            char c = str.charAt(i);
            System.out.println("char {} : {}" + i + c);
            if (c == 34) {
                newStr += "\\\"";
                continue;
            }
            replacement = REPLACEMENT_CHARS[c];
            if (replacement == null) {
                newStr += c;
                continue;
            }
            newStr += replacement;
        }
        newStr += "\"";
        return newStr;
    }

    private String toJsonList(Object[] array) {
        JsonArrayBuilder jsArray = Json.createArrayBuilder();
        if (array.length !=0) {
            Object elem0 = array[0];
            String typeName = elem0.getClass().getSimpleName();
            System.out.println("Elem type : " + elem0.getClass().getSimpleName());
            for(Object elem: array) {
                switch (typeName) {
                    case "Byte":
                    case "Short":
                    case "Integer": {
                        jsArray.add((int) elem);
                        break;
                    }
                    case "Long":{
                        jsArray.add((long) elem);
                        break;
                    }
                    case "Float":
                    case "Double": {
                        jsArray.add((double) elem);
                        break;
                    }
                    case "String":
                    case "Character": {
                        jsArray.add((String) elem);
                        break;
                    }
                }
            }
        }
        return jsArray.build().toString();
    }

    private String toJsonPrimitive(Object testObj) {
        JsonValue jsonPrimitive = JsonValue.NULL;
        Class<?> fieldType = testObj.getClass();
        System.out.println("Primitive testObj");
        if (testObj instanceof Byte) {
            jsonPrimitive = Json.createValue((byte) testObj);
        } else if (testObj instanceof Short) {
            jsonPrimitive = Json.createValue((short) testObj);
        } else if (testObj instanceof Integer) {
            jsonPrimitive = Json.createValue((int) testObj);
        } else if(testObj instanceof Boolean) {
            jsonPrimitive = ((boolean) testObj) ? JsonValue.TRUE : JsonValue.FALSE;
        } else if(testObj instanceof Long) {
            jsonPrimitive = Json.createValue((long) testObj);
        } else if(testObj instanceof Character) {
            jsonPrimitive = Json.createValue(testObj.toString());
        } else if(testObj instanceof Float || testObj instanceof Double) {
            jsonPrimitive = Json.createValue(((Number) testObj).doubleValue());
        }
        return jsonPrimitive.toString();
    }

    private String toJsonArray(Object array) {
        System.out.println("Type Array : " + array.getClass().getComponentType() + " " + array.getClass().getSimpleName());

        String typeName = array.getClass().getSimpleName();

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
        return jsArray.build().toString();
    }

    private void init() {
        typesPrimitiveInt.add(int.class);
        typesPrimitiveInt.add(byte.class);
        typesPrimitiveInt.add(short.class);
        typesPrimitiveInt.add(Integer.class);
        typesPrimitiveInt.add(Byte.class);
        typesPrimitiveInt.add(Short.class);

        typesPrimitive.add(int.class);
        typesPrimitive.add(byte.class);
        typesPrimitive.add(short.class);
        typesPrimitive.add(char.class);
        typesPrimitive.add(double.class);
        typesPrimitive.add(float.class);
        typesPrimitive.add(long.class);

        typesPrimitive.add(Integer.class);
        typesPrimitive.add(Byte.class);
        typesPrimitive.add(Short.class);
        typesPrimitive.add(Character.class);
        typesPrimitive.add(Double.class);
        typesPrimitive.add(Float.class);
        typesPrimitive.add(Long.class);

        REPLACEMENT_CHARS[60] = "\\u003c";
        REPLACEMENT_CHARS[62] = "\\u003e";
        REPLACEMENT_CHARS[38] = "\\u0026";
        REPLACEMENT_CHARS[61] = "\\u003d";
        REPLACEMENT_CHARS[39] = "\\u0027";
        // REPLACEMENT_CHARS[34] = "\\u0022";
    }
}
