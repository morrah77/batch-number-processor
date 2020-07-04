package com.morrah77.batch.arithmetic;

public class Calculator {
    public static void add(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] += factor;
        }
    }
    
    public static void subtract(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= factor;
        }
    }

    public static void multiply(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= factor;
        }
    }

    public static void divide(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] /= factor;
        }
    }

    public static void mod(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] %= factor;
        }
    }
}