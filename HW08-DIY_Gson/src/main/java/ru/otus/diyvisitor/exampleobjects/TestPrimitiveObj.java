package ru.otus.diyvisitor.exampleobjects;

import java.util.Objects;

public class TestPrimitiveObj {
    private final int prim1;
    private final String prim2;
    private final float prim3;

    public TestPrimitiveObj(int prim1, String prim2, float prim3) {
        this.prim1 = prim1;
        this.prim2 = prim2;
        this.prim3 = prim3;
    }

    @Override
    public String toString() {
        return "TestPrimitiveObj{" +
                "prim1=" + prim1 +
                ", prim2='" + prim2 + '\'' +
                ", value3=" + prim3 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestPrimitiveObj that = (TestPrimitiveObj) o;
        return prim1 == that.prim1 &&
                prim3 == that.prim3 &&
                Objects.equals(prim2, that.prim2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prim1, prim2, prim3);
    }
}
