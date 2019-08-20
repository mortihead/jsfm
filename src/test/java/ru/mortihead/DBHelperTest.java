package ru.mortihead;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBHelperTest {
    DBHelper dbHelper;

    @Before
    public void initTest() {
        dbHelper = new DBHelper();

    }

    @Test
    public void test1() {
        System.out.println("test1111444yyy");
        int num = 0;
        dbHelper.doTest();
        assertEquals(num, 0);

    }

}
