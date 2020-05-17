package ru.otus.diyvisitor.exampleobjects;

import java.util.*;

public class TestObj {
    private final int value1;
    private final String value2;
    private final double value3;
    private final TestPrimitiveObj testPrim;
    private final int[] arrInt;
    private final String[] arrString;
    private final TestPrimitiveObj[] primObjects;
    private final Set<TestPrimitiveObj> primList;

    public TestObj(int value1, String value2, double value3, TestPrimitiveObj testPrim,
                   int[] arrInt, String[] arrString, TestPrimitiveObj[] primObjects,
                   Set<TestPrimitiveObj> primList) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.testPrim = testPrim;
        this.arrInt = arrInt;
        this.arrString = arrString;
        this.primObjects = primObjects;
        this.primList = primList;
    }

    @Override
    public String toString() {
        return "TestObj{" +
                "value1=" + value1 +
                ", value2='" + value2 + '\'' +
                ", value3=" + value3 +
                ", testPrim=" + (testPrim == null ? "" : testPrim.toString()) +
                ", arrInt=" + Arrays.toString(arrInt) +
                ", arrString=" + Arrays.toString(arrString) +
                ", primObjects=" + Arrays.toString(primObjects) +
                ", primList=" + (primList == null ? "" : primList.toString()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestObj that = (TestObj) o;
        return value1 == that.value1 &&
                value3 == that.value3 &&
                Objects.equals(value2, that.value2) &&
                Objects.deepEquals(that.testPrim, this.testPrim) &&
                Arrays.equals(that.arrInt, this.arrInt) &&
                Arrays.equals(that.arrString,  this.arrString) &&
                Arrays.deepEquals(that.primObjects, this.primObjects) &&
                ((primList == null && that.primList == null) || (primList.size() == that.primList.size() && primList.containsAll(that.primList)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3);
    }
}
