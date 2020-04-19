package ru.otus.diygson;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestOfPrimitives {
  private final int value1;
  private final String value2;
  private final int value3;
  private final TestObj testObj;
  private final int[] arrInt;
  private final String[] arrString;
  private final double[] arrDouble;
  private final TestObj[] arrObj;
  private final List list;

  TestOfPrimitives(int value1, String value2, int value3, TestObj testObj,
                   int[] arrInt, String[] arrStr, double[] arrDouble, TestObj[] arrObj, List list) {
    this.value1 = value1;
    this.value2 = value2;
    this.value3 = value3;
    this.testObj = testObj;
    this.arrInt = arrInt;
    this.arrString = arrStr;
    this.arrDouble = arrDouble;
    this.arrObj = arrObj;
    this.list = list;
  }

  @Override
  public String toString() {
    return "BagOfPrimitives{" +
        "value1=" + value1 +
        ", value2='" + value2 + '\'' +
        ", value3=" + value3 +
        ", arrInt=" + Arrays.toString(arrInt) +
        ", arrString=" + Arrays.toString(arrString) +
        ", arrDouble=" + Arrays.toString(arrDouble) +
        ", arrObj=" + Arrays.deepToString(arrObj) +
    '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TestOfPrimitives that = (TestOfPrimitives) o;
    return value1 == that.value1 &&
        value3 == that.value3 &&
        Objects.equals(value2, that.value2) &&
        testObj.equals(that.testObj) &&
        Arrays.equals(arrInt, that.arrInt) &&
        Arrays.equals(arrDouble, that.arrDouble) &&
        Arrays.equals(arrString, that.arrString) &&
        Arrays.equals(arrObj, that.arrObj);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value1, value2, value3, arrInt, arrDouble, arrString, arrObj);
  }
}
