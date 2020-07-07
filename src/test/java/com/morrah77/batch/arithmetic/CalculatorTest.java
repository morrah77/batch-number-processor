package com.morrah77.batch.arithmetic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

// TODO add corner cases for negatives, partials, large values; consider refactoring tests to parameterized form
class CalculatorTest {
    private List<Float> sourceData;

    @BeforeEach
    void setup() {
        initSourceData();
    }

    @Test
    void BatchAddTest() {
        List<Float> expected = new ArrayList<>(Arrays.asList(2f, 3f, 4f));
        Calculator.add(sourceData, 1f);
        assertIterableEquals(expected, sourceData, String.format("Add " +
                "produced unexpected" +
                " result: " +
                "%s " +
                "expected, %s actual", expected, sourceData));
    }

    @Test
    void BatchSubtractTest() {
        List<Float> expected = new ArrayList<>(Arrays.asList(0f, 1f, 2f));
        Calculator.subtract(sourceData, 1f);
        assertIterableEquals(expected, sourceData, String.format("Add produced unexpected result: %s " +
                "expected, %s actual", expected, sourceData));
    }

    @Test
    void BatchMultiplyTest() {
        List<Float> expected = new ArrayList<>(Arrays.asList(2f, 4f, 6f));
        Calculator.multiply(sourceData, 2f);
        assertIterableEquals(expected, sourceData, String.format("Add produced unexpected result: %s " +
                "expected, %s actual", expected, sourceData));
    }

    @Test
    void BatchDivideTest() {
        List<Float> expected = new ArrayList<>(Arrays.asList(.5f, 1f, 1.5f));
        Calculator.divide(sourceData, 2f);
        assertIterableEquals(expected, sourceData, String.format("Add produced unexpected result: %s " +
                "expected, %s actual", expected, sourceData));
    }

    @Test
    void BatchModTest() {
        List<Float> expected = new ArrayList<>(Arrays.asList(1f, 0f, 1f));
        Calculator.mod(sourceData, 2f);
        assertIterableEquals(expected, sourceData, String.format("Add produced unexpected result: %s " +
                "expected, %s actual", expected, sourceData));
    }

    private void initSourceData() {
        sourceData = new ArrayList<>(Arrays.asList(1f, 2f, 3f));
    }
}