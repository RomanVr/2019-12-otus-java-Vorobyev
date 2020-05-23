package ru.otus.diyvisitor;


import com.google.gson.Gson;
import ru.otus.diyvisitor.exampleobjects.TestPrimitiveObj;
import ru.otus.diyvisitor.exampleobjects.TestObj;

import java.util.HashSet;
import java.util.Set;

public class Demo {
    public static void main(String[] args) throws IllegalAccessException {
        TestPrimitiveObj testPrim = new TestPrimitiveObj(5, "6", 7);
        TestPrimitiveObj testPrim1 = new TestPrimitiveObj(51, "61", 71);
        TestPrimitiveObj[] primObjs = {testPrim, testPrim1};
        Set<TestPrimitiveObj> primList = new HashSet<>();
        primList.add(testPrim);
        primList.add(testPrim1);
        int[] intArr = {4, 5, 6};
        String[] strArr = {"s1", "2", "S3"};

        TestObj testObj = new TestObj(1, "22", 3.2, testPrim,
                intArr, strArr, primObjs, primList);

        String DIYjson = new DIYGson().toJson(testObj);

        String gson = new Gson().toJson(testObj);

        TestObj testObjRestored = new Gson().fromJson(DIYjson, TestObj.class);

        System.out.println(testObj);
        System.out.println(testObjRestored);
        System.out.println();
        System.out.println("Converted is: " + testObj.equals(testObjRestored));
        System.out.println();

        System.out.println("Gson : " + gson);
        System.out.println("DIYj : " + DIYjson);
    }
}
