package com.test.shortener.service.impl;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Base62ConversionServiceTest {

    private final Base62ConversionService serviceUnderTest = new Base62ConversionService();

    @Test
    public void encodeLessThan62Test() {
        String actual = serviceUnderTest.encode(1);
        assertEquals("b", actual);
    }

    @Test
    public void encodeMoreThan62Test() {
        String actual = serviceUnderTest.encode(63);
        assertEquals("bb", actual);
    }

    @Test
    public void decodeSingleCharacterTest() {
        long actual = serviceUnderTest.decode("l");
        assertEquals(11, actual);
    }

}