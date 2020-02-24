package ru.otus.TestAnnotation;

import ru.otus.TestAnnotation.Annotation.After;
import ru.otus.TestAnnotation.Annotation.Before;
import ru.otus.TestAnnotation.Annotation.Test;

public class SampleForTests {
    @After
    public void methodAfter() {
        System.out.println("Run method After");
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
