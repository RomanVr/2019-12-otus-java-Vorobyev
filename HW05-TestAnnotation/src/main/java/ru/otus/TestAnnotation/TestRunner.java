package ru.otus.TestAnnotation;

import ru.otus.TestAnnotation.Annotation.After;
import ru.otus.TestAnnotation.Annotation.Before;
import ru.otus.TestAnnotation.Annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static <T> void run(Class<T> clazz) {
        List<Method> methodsTest = getMethods(clazz, Test.class);
        List<Method> methodsAfter = getMethods(clazz, After.class);
        List<Method> methodsBefore = getMethods(clazz, Before.class);

        T testObj = null;

        int passed = 0;
        for (Method m : methodsTest) {
            try {
                testObj = clazz.getDeclaredConstructor().newInstance();
                for (Method methodBefore : methodsBefore) {
                    methodBefore.invoke(testObj);
                }

                m.invoke(testObj);
                passed += 1;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.out.println("test " + m.getName() + " failed: " + e.getCause());
            } finally {
                for (Method methodAfter : methodsAfter) {
                    try {
                        methodAfter.invoke(testObj);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.printf("Total tests: %d, Passed: %d, Failed: %d%n", methodsTest.size(), passed, methodsTest.size() - passed);
    }

    private static <T> List<Method> getMethods(Class<T> clazz, Class<? extends Annotation> annClazz) {
        List<Method> methods = new ArrayList<>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(annClazz)) {
                methods.add(m);
            }
        }
        return methods;
    }
}