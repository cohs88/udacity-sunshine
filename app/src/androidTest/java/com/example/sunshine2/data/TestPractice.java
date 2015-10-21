package com.example.sunshine2.data;

import android.test.AndroidTestCase;

/**
 * Created by servando on 10/20/2015.
 */
public class TestPractice extends AndroidTestCase {


    /***
     * this gets run before every test
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testThatDemostratesAssertions()
    {
        int a = 5;
        int b = 3;
        int c = 5;
        int d = 10;

        assertEquals("X should be equal", a, c);
        assertTrue("Y should be true", d > a);
        assertFalse("Z should be false", a == b);

        if (b > d) {
            fail("XX should never happen");
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
