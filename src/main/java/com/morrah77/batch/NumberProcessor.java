package com.morrah77.batch;

import com.morrah77.batch.arithmetic.Calculator;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.Stack;

public class NumberProcessor {
    public static final String PROC_COMMAND_QUIT="quit";
    public static final String PROC_COMMAND_UNDO="undo";

    private InputStream inputStream;
    private OutputStream outputStream;
    private float[] data;
    Stack<float[]> dataStack;
    private boolean isStarted = false;

    public NumberProcessor(InputStream inputStream, OutputStream outputStream, float[] data) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.data = data;
        dataStack = new Stack<>();
    }

    public void run() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Scanner scanner = new Scanner(inputStream);
        String input;
        outputData();
        Command command;
        isStarted = true;
        do {
            input = scanner.next();
            try {
                command = parseInput(input);
            } catch (Exception e) {
                output("Invalid input. Please try again.");
                continue;
            }
            executeCommand(command);
        } while (isStarted);
    }

    // TODO move command execution to Command class
    private void executeCommand(Command command) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        switch (command.name) {
            case PROC_COMMAND_QUIT:
                executeQuitCommand(command);
                break;
            case PROC_COMMAND_UNDO:
                executeUndoCommand(command);
                break;
            default:
                executeCalculatorCommand(command);
        }
    }

    private void executeQuitCommand(Command command) {
        isStarted = false;
    }

    private void executeUndoCommand(Command command) throws IOException {
        if (dataStack.empty()) {
            output("Nothing to " + PROC_COMMAND_UNDO);
            return;
        }
        restoreStatus();
        outputData();
    }

    private void executeCalculatorCommand(Command command) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        saveStatus();
        Calculator.class.getMethod(command.name, float[].class, float.class).invoke(Calculator.class,
                data, command.value);
        outputData();
    }

    private void restoreStatus() {
        data = dataStack.pop();
    }

    private void saveStatus() {
        dataStack.push(data.clone());
    }

    // TODO consider command enum creation
    private Command parseInput(String input) {
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

    // TODO improve it!
    private void outputData() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String formatForPartial = "%s";
        String formatForWhole = "%d";
        for (float sourceDatum : data) {
            if (sourceDatum == (long)sourceDatum) {
                stringBuilder.append(String.format(formatForWhole, (long)sourceDatum));
            } else {
                stringBuilder.append(String.format(formatForPartial, sourceDatum));
            }
            stringBuilder.append(';');
        }
        output(stringBuilder.toString());
    }

    private void output(String value) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.write(value);
        writer.newLine();
        writer.flush();
    }
}
