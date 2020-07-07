package com.morrah77.batch;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class Application {
    static List<Float> sourceData;
    static NumberProcessor numberProcessor;

    public static void main(String[]args){
        try {
            checkAndParseArguments(args);
        } catch (RuntimeException e) {
            printMessageAndExit(e.getMessage());
        }
        numberProcessor = new NumberProcessor(System.in, System.out, sourceData);
        try {
            numberProcessor.run();
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            printMessageAndExit(e.getMessage());
        }
    }

    private static void printMessageAndExit(String message) {
        System.out.println(String.format("%s\nInvalid input. Shutting down...", message));
        System.exit(1);
    }

    private static void checkAndParseArguments(String[]args) throws RuntimeException {
        checkArgumentLength(args);
        sourceData = parseNumberList(args[0]);
    }

    private static void checkArgumentLength(String[]args) {
        if (args.length < 1) {
            throw new RuntimeException("Not enough arguments!");
        }
    }

    private static List<Float> parseNumberList(String arg) throws NumberFormatException {
        List<Float> result;
        String[] parts = arg.split(";");
        result = new ArrayList<>();
        for (int i = 0; i < parts.length; i++) {
            result.add(Float.parseFloat(parts[i]));
        }
        return result;
    }
}
