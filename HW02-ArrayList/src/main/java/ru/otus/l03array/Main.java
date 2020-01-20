package ru.otus.l03array;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String ...args) {
        List<String> listExample = new DIYArrayList<>();

        System.out.println("Empty : " + listExample.isEmpty());

        final int sizeList = 50;
        for(int i = 0; i < sizeList; i += 1){
            listExample.add("item" + i);
        }

        System.out.println("------------Source List-----------");
        for(final Object item : listExample){
            System.out.println(item);
        }
        System.out.println("------------Add List-----------");
        final int sizeArray = 20;
        final String[] newItems = new String[sizeArray];

        for (int i = 0; i < sizeArray; i += 1) {
            newItems[i] = "item10" + i;
        }
        Collections.addAll(listExample, newItems);
        for(final Object item : listExample){
            System.out.println(item);
        }

        System.out.println("--------------------destList-------------------");
        final List<String> destList = new DIYArrayList<>();
        for(final Object item : listExample){
            destList.add("n");
        }
        System.out.println("Size dest :" + destList.size());
        Collections.copy(destList, listExample);

        for(final Object item : destList){
            System.out.println(item);
        }
        System.out.println("--------------------Sort with Comparator------------");
        final Comparator<String> comp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        Collections.sort(listExample, comp);
        for(final Object item : listExample){
            System.out.println(item);
        }

        listExample.clear();

        System.out.println("Empty after method clear() : " + listExample.isEmpty());
    }
}
