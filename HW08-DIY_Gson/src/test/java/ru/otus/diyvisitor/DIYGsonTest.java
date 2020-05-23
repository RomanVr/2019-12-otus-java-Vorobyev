package ru.otus.diyvisitor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DIYGsonTest {
    private DIYGson jsonWriter;

    @BeforeEach
    void setUp() {
        this.jsonWriter = new DIYGson();
    }
    @Test
    void toJson() {
        Gson gson = new GsonBuilder().serializeNulls().create();

        assertEquals(gson.toJson(null), jsonWriter.toJson(null));
        System.out.println("Passed Null");
        assertEquals(gson.toJson((byte)1), jsonWriter.toJson((byte)1));
        System.out.println("Passed Byte");
        assertEquals(gson.toJson((short)1f), jsonWriter.toJson((short)1f));
        System.out.println("Passed Short");
        assertEquals(gson.toJson(1), jsonWriter.toJson(1));
        System.out.println("Passed Int");
        assertEquals(gson.toJson(1L), jsonWriter.toJson(1L));
        System.out.println("Passed long");
        assertEquals(gson.toJson(1f), jsonWriter.toJson(1f));
        System.out.println("Passed float");
        assertEquals(gson.toJson(1d), jsonWriter.toJson(1d));
        System.out.println("Passed double");
        assertEquals(gson.toJson("aaa"), jsonWriter.toJson("aaa"));
        System.out.println("Passed String 1");
        assertEquals(gson.toJson("a\"aa"), jsonWriter.toJson(("a\"aa")));
        System.out.println("Passed String 2");
        assertEquals(gson.toJson("a'aa"), jsonWriter.toJson(("a'aa")));
        System.out.println("Passed String 3");
        assertEquals(gson.toJson("a\"aa"), jsonWriter.toJson(("a\"aa")));
        System.out.println("Passed String 4");

        assertEquals(gson.toJson('a'), jsonWriter.toJson('a'));
        System.out.println("Passed char");
        assertEquals(gson.toJson(new int[] {1, 2, 3}), jsonWriter.toJson(new int[] {1, 2, 3}));
        System.out.println("Passed int[]");
        assertEquals(gson.toJson(List.of(1, 2 ,3)), jsonWriter.toJson(List.of(1, 2 ,3)));
        System.out.println("Passed List[]");
        assertEquals(gson.toJson(Collections.singletonList("1")), jsonWriter.toJson(Collections.singletonList("1")));
    }

}