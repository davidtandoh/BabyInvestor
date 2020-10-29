package com.example.babyinvestor;

import org.junit.Test;

import static com.example.babyinvestor.Utils.getOneWeekEpochDate;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getOneWeekEpochDate_isCorrect(){
        assertEquals(1598364857, getOneWeekEpochDate());
    }
}