package com.morrah77.batch.arithmetic;

import java.util.List;

// TODO rewrite using streams
// REM the feature of streams is copying of data doing not change source one, so, this in-place-changing calculator
//is not the best place of streaming
public class Calculator {
    public static void add(final List<Float> arr, Float factor) {
        // Some `overheaded` way of using streams here - just to show the possibility; is not repeated in other methods
        // Usage in lambda: need final but changeable var, so, use one passed&&changed by ref
        final int[] i = {0};
        arr.stream().forEach(it -> { arr.set(i[0], it += factor);i[0]++;});

        // Here's the simplest way of changing list in-place
        // arr.replaceAll(it -> it + factor);
    }
    
    public static void subtract(List<Float> arr, Float factor) {
        arr.replaceAll(it -> it - factor);
    }

    public static void multiply(List<Float> arr, Float factor) {
        arr.replaceAll(it -> it * factor);
    }

    public static void divide(List<Float> arr, Float factor) {
        arr.replaceAll(it -> it / factor);
    }

    public static void mod(List<Float> arr, Float factor) {
        arr.replaceAll(it -> it % factor);
    }
}