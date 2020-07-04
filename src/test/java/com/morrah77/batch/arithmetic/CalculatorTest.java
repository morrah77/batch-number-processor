package com.morrah77.batch.arithmetic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CalculatorTest {
    private Calculator calculator;
    private float[] sourceData;

    @BeforeEach
    void setup() {
        calculator = new Calculator();
        initSourceData();
    }

    @Test
    void BatchAddTest() {
        float[] expected = {2, 3, 4};
        calculator.add(sourceData, 1);
        assertArrayEquals(expected, sourceData, String.format("Add produced unexpected result: %s " +
                "expected, %s actual", expected, sourceData));
    }

    @Test
    void BatchSubtractTest() {
        float[] expected = {0, 1, 2};
        calculator.subtract(sourceData, 1);
        assertArrayEquals(expected, sourceData, String.format("Add produced unexpected result: %s " +
                "expected, %s actual", expected, sourceData));
    }

    @Test
    void BatchMultiplyTest() {
        float[] expected = {2, 4, 6};
        calculator.multiply(sourceData, 2);
        assertArrayEquals(expected, sourceData, String.format("Add produced unexpected result: %s " +
                "expected, %s actual", expected, sourceData));
    }

    @Test
    void BatchDivideTest() {
        float[] expected = {0.5f, 1, 1.5f};
        calculator.divide(sourceData, 2);
        assertArrayEquals(expected, sourceData, String.format("Add produced unexpected result: %s " +
                "expected, %s actual", expected, sourceData));
    }

    @Test
    void BatchModTest() {
        float[] expected = {1, 0, 1};
        calculator.mod(sourceData, 2);
        assertArrayEquals(expected, sourceData, String.format("Add produced unexpected result: %s " +
                "expected, %s actual", expected, sourceData));
    }

    private void initSourceData() {
        sourceData = new float[]{1, 2, 3};
    }
}