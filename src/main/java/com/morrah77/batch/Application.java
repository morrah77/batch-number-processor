package com.morrah77.batch;

import com.morrah77.batch.arithmetic.Calculator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.Stack;

class Application {
    public static final String PROC_COMMAND_QUIT="quit";
    public static final String PROC_COMMAND_UNDO="undo";

    static float[] sourceData;
    static Stack<float[]> dataStack;
    static Calculator calculator;

    public static void main(String[]args){
        try {
            checkAndarseArguments(args);
        } catch (RuntimeException e) {
            printMessageAndExit(e.getMessage());
        }
        calculator = new Calculator();
        dataStack = new Stack<>();
        try {
            runMainCycle();
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            printMessageAndExit(e.getMessage());
        }
    }

    private static void runMainCycle() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Scanner scanner = new Scanner(System.in);
        String input;
        printSourceData();
        Command command;
        while (true){
            input = scanner.next();
            try {
                command = parseInput(input);
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            if (command.name.equals(PROC_COMMAND_QUIT)) {
                System.exit(0);
            }
            if (command.name.equals(PROC_COMMAND_UNDO)) {
                if (dataStack.empty()) {
                    System.out.println("Nothing to " + PROC_COMMAND_UNDO);
                    continue;
                }
                restoreStatus();
                printSourceData();
                continue;
            }
            saveStatus();
            calculator.getClass().getMethod(command.name, float[].class, float.class).invoke(calculator,
             sourceData, command.value);
            printSourceData();
        }
    }

    static void restoreStatus() {
        sourceData = dataStack.pop();
    }

    static void saveStatus() {
        dataStack.push(sourceData.clone());
    }

    private static Command parseInput(String input) {
        if (input.length() <= 1) {
            throw new RuntimeException("Invalid input");
        }
        if (input.equals(PROC_COMMAND_QUIT) || input.equals(PROC_COMMAND_UNDO)) {
            return new Command(input, 0);
        }
        switch (input.charAt(0)) {
            case '+':
                return new Command("add", Float.parseFloat(input.substring(1)));
            case '*':
                return new Command("multiply", Float.parseFloat(input.substring(1)));
            case '%':
                return new Command("mod", Float.parseFloat(input.substring(1)));
            default:
                throw new RuntimeException("Unsupported command");
        }
    }

    private static class Command {
        String name;
        float value;
        Command(String name, float value) {
            this.name = name;
            this.value = value;
        }
        private boolean isProcessorRelated() {
            return this.name.equals(PROC_COMMAND_QUIT) || this.name.equals(PROC_COMMAND_UNDO);
        }
    }

//    enum CommandName {
//        quit,
//        undo,
//        add,
//        multiply,
//        mod;
//    }

    private static void printSourceData() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String formatForPartial = "%s";
        String formatForWhole = "%d";
        for (float sourceDatum : sourceData) {
            if (sourceDatum == (long)sourceDatum) {
                stringBuilder.append(String.format(formatForWhole, (long)sourceDatum));
            } else {
                stringBuilder.append(String.format(formatForPartial, sourceDatum));
            }
            stringBuilder.append(';');
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(stringBuilder.toString());
        writer.newLine();
        writer.flush();
//        System.out.println(stringBuilder.toString());
    }

    private static void printMessageAndExit(String message) {
        System.out.println(String.format("%s\nInvalid input. Shutting down...", message));
        System.exit(1);
    }

    private static void checkAndarseArguments(String[]args) throws RuntimeException {
        checkArgumentLength(args);
        sourceData = parseArray(args[0]);
    }

    private static void checkArgumentLength(String[]args) {
        if (args.length < 1) {
            throw new RuntimeException("Not enough arguments!");
        }
    }

    private static float[] parseArray(String arg) throws NumberFormatException {
        float[] result;
        String[] parts = arg.split(";");
        result = new float[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Float.parseFloat(parts[i]);
        }
        return result;
    }
}
