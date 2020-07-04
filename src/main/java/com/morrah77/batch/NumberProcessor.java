package com.morrah77.batch;

import java.io.InputStream;
import java.io.OutputStream;

public class NumberProcessor {
    private InputStream inputStream;
    private OutputStream outputStream;
    private float[] data;

    public NumberProcessor(InputStream inputStream, OutputStream outputStream, float[] data) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.data = data;
    }

    public void run() {

    }
}
