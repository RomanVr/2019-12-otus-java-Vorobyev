package ru.otus.diygson;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoDIYGson {
    public static void main(String[] args) {

        TestObj testObj = new TestObj(1,"2",3);
        int[] intArr = {4, 5, 6};
        double[] doubleArr = {4.4, 5.6, 6.7};
        TestObj[] objArr = { new TestObj(1, "s", 1), new TestObj(2, "t", 2)};
        String[] strArr = { "ee", "rr"};
        List list = new ArrayList();
        list.add(45);
        list.add(65);
        TestOfPrimitives obj = new TestOfPrimitives(
                22, "test", 10, testObj, intArr, strArr, doubleArr, objArr, list);

        DIYGson diyGson = new DIYGson();
        diyGson.types();

        Gson gson = new Gson();


        String json2 = gson.toJson(list);

        String json = diyGson.toJson(list);

        /*TestOfPrimitives obj2 = gson.fromJson(json, TestOfPrimitives.class);
        System.out.println("Compare Json: " + json.equals(json2));
        System.out.println("Compare Object: " + obj.equals(obj2));

        System.out.println("Result : " + obj2.toString());*/
        System.out.println("json : " + json);
        System.out.println("json2: " + json2);

    }
}
