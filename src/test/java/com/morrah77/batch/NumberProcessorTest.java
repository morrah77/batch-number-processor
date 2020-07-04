package com.morrah77.batch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

// TODO generalize processor initialization, add more corner cases
class NumberProcessorTest {
    InputStream inputStream;
    OutputStream outputStream;
    float[] data;
    NumberProcessor numberProcessor;

    @BeforeEach
    void setUp() {
        inputStream = new ByteArrayInputStream("1;0;-1.5;4000.0\nundo\n-1\n+2\n*5\n%3\nundo\nquit".getBytes());
        outputStream = new ByteArrayOutputStream(1200);
        data = new float[]{1f, 0f, -1.5f, 4000.0f};
        numberProcessor = new NumberProcessor(inputStream, outputStream, data);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void run() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String expected = "1;0;-1.5;4000;\n" +
                "Invalid input. Please try again.\n" +
                "Nothing to undo\n" +
                "Invalid input. Please try again.\n" +
                "3;2;0.5;4002;\n" +
                "15;10;2.5;20010;\n" +
                "0;1;2.5;0;\n" +
                "15;10;2.5;20010;\n";
        Assertions.assertDoesNotThrow(() -> numberProcessor.run());
        Assertions.assertEquals(expected, outputStream.toString());
    }
}