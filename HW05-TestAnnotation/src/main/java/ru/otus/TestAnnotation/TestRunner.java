package ru.otus.TestAnnotation;

import ru.otus.TestAnnotation.Annotation.After;
import ru.otus.TestAnnotation.Annotation.Before;
import ru.otus.TestAnnotation.Annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static <T> void run(Class<T> clazz) {
        List<Method> methodsTest = getTests(clazz);
        List<Method> methodsAfter = getAfterMethods(clazz);
        List<Method> methodsBefore = getBeforeMethods(clazz);

        T testObj;
        T beforeObj = null;
        T afterObj = null;
        try {
            beforeObj = clazz.getDeclaredConstructor().newInstance();
            afterObj = clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        int passed = 0;
        for (Method m : methodsTest) {
            try {
                for (Method methodBefore : methodsBefore) {
                    methodBefore.invoke(beforeObj);
                }
                testObj = clazz.getDeclaredConstructor().newInstance();

                m.invoke(testObj);

                passed += 1;
            } catch (InvocationTargetException e) {
                System.out.println("test " + m.getName() + " failed: " + e.getCause());
            } catch (IllegalAccessException e) {
                System.out.println("Invalid @Test: " + m.getName());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } finally {
                for (Method methodAfter : methodsAfter) {
                    try {
                        methodAfter.invoke(afterObj);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

         System.out.printf("Total tests: %d, Passed: %d, Failed: %d%n", methodsTest.size(), passed, methodsTest.size() - passed);
    }

    private static <T> List<Method> getBeforeMethods(Class<T> clazz) {
        List<Method> methodsBefore = new ArrayList<>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Before.class)) {
                methodsBefore.add(m);
            }
        }
        return methodsBefore;
    }

    private static <T> List<Method> getAfterMethods(Class<T> clazz) {
        List<Method> methodsAfter = new ArrayList<>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(After.class)) {
                methodsAfter.add(m);
            }
        }
        return methodsAfter;
    }

    private static <T> List<Method> getTests(Class<T> clazz) {
        List<Method> methodsTest = new ArrayList<>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                methodsTest.add(m);
            }
        }
        return methodsTest;
    }
}
