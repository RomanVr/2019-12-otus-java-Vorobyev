package ru.otus.TestAnnotation;

import ru.otus.TestAnnotation.Annotation.After;
import ru.otus.TestAnnotation.Annotation.Before;
import ru.otus.TestAnnotation.Annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    static void run(Object testObj) {
        int passed = 0;
        List<Method> methodsTest = new ArrayList<>();
        List<Method> methodsAfter = new ArrayList<>();
        List<Method> methodsBefore = new ArrayList<>();
        for(Method m: testObj.getClass().getDeclaredMethods()){
            if(m.isAnnotationPresent(Test.class)) {
                methodsTest.add(m);
            }
            if(m.isAnnotationPresent(After.class)) {
                methodsAfter.add(m);
            }
            if(m.isAnnotationPresent(Before.class)) {
                methodsBefore.add(m);
            }
        }
        for(Method m: methodsTest){
            try {
                for(Method methodBefore: methodsBefore) {
                    methodBefore.invoke(testObj, null);
                }

                m.invoke(testObj, null);

                for(Method methodAfter: methodsAfter) {
                    methodAfter.invoke(testObj, null);
                }
                passed += 1;
            } catch (InvocationTargetException e) {
                System.out.println("test " + m.getName() + " failed: " + e.getCause());
            } catch (IllegalAccessException e) {
                System.out.println("Invalid @Test: " + m.getName());
            }
        }
        System.out.printf("Total tests: %d, Passed: %d, Failed: %d%n", methodsTest.size(), passed, methodsTest.size() - passed);
    }
}
