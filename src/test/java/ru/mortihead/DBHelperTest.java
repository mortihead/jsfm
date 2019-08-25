package ru.mortihead;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBHelperTest {
    DBHelper dbHelper;

    @Before
    public void initTest() {
        dbHelper = new DBHelper();

    }

    @Ignore
    public void test1() {
        System.out.println("test");
        int num = 0;
        dbHelper.doTest();
        assertEquals(num, 0);

    }

}
