package com.morrah77.batch.arithmetic;

public class Calculator {
    public Calculator() {
    }

    public void add(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] += factor;
        }
    }
    
    public void subtract(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= factor;
        }
    }

    public void multiply(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= factor;
        }
    }

    public void divide(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] /= factor;
        }
    }

    public void mod(float[] arr, float factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] %= factor;
        }
    }
}