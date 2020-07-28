package ru.otus.TestAnnotation;

import ru.otus.TestAnnotation.Annotation.After;
import ru.otus.TestAnnotation.Annotation.Before;
import ru.otus.TestAnnotation.Annotation.Test;

public class SampleForTests {
    @After
    public void methodAfter1() {
        System.out.println("Run method After1");
    }
    @After
    public void methodAfter2() {
        System.out.println("Run method After2");
    }
    @Before
    public void methodBefore() {
        System.out.println("Run method Before");
    }

    @Test
    public void methodPass() {
        System.out.println("Test passed");
    }

    @Test
    public void methodFail() {
        throw new RuntimeException("fail");
    }

    public void method() {

    }
}
