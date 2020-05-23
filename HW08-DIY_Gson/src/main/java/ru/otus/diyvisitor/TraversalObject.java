package ru.otus.diyvisitor;

import ru.otus.diyvisitor.types.*;
import ru.otus.diyvisitor.visitor.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

public class TraversalObject {
    public void toJson(Object obj, Service service) throws IllegalAccessException {
        if (obj == null) return;

        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);

            if (Modifier.isStatic(field.getModifiers())) {

            } else if (field.getType().isPrimitive()) {//Primitive
                new ProcessingPrimitive(field, obj).accept(service);

            } else if (field.getType().isAssignableFrom(String.class)) {//String
                new ProcessingString(field, obj).accept(service);

            } else if(field.getType().isAssignableFrom(List.class)) {//List
                List list = (List) field.get(obj);
                Object[] array = list.toArray();
                toJsonArray(array, field, service);

            } else if(field.getType().isAssignableFrom(Set.class)) {
                Set set = (Set) field.get(obj);
                if (set == null) {
                    new ProcessingArrayNull(field);
                    continue;
                }
                Object[] array = set.toArray();
                toJsonArray(array, field, service);

            } else if (field.getType().isArray()) {//Array
                Object array = field.get(obj);
                if (array == null) {
                    new ProcessingArrayNull(field);
                    continue;
                }
                toJsonArray(array, field, service);

            } else {//Object
                JsonObjectBuilder jsonObjectBuilderNew = Json.createObjectBuilder();
                Object value = field.get(obj);
                if (value == null) {
                    new ProcessingObjectNull(field);
                    continue;
                }
                toJson(value, new JsonObjectService(jsonObjectBuilderNew));
                new ru.otus.diyvisitor.types.ProcessingObject(field, jsonObjectBuilderNew).accept(service);
            }
        }
    }

    private void toJsonArray(Object array, Field field, Service service) throws IllegalAccessException {
        if (array.getClass().getComponentType().isPrimitive() || //Если тип элемента массива примитивный или String то вызываем процессинг заполнения
            array.getClass().getComponentType().getSimpleName().equals(String.class.getSimpleName())) {
            new ProcessingArray(field, array).accept(service);
        } else {// если тип элемента массива не примитивный (объект или массив) то рекурсивно вызываем toJson
            // передавая новый сервис
            JsonArrayBuilder jsArr = Json.createArrayBuilder();
            Object[] arrCast = (Object[]) array;
            for (Object elem : arrCast) {
                JsonObjectBuilder jsonObjectBuilderNew = Json.createObjectBuilder();
                toJson(elem, new JsonObjectService(jsonObjectBuilderNew));
                jsArr.add(jsonObjectBuilderNew);
            }
            new ProcessingObjectInArray(field, jsArr).accept(service);
        }
    }
}
