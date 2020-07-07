package com.morrah77.batch;

import com.morrah77.batch.arithmetic.Calculator;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

// TODO rewrite using streams, ops chain
public class NumberProcessor {
    public static final String PROC_COMMAND_QUIT = "quit";
    public static final String PROC_COMMAND_UNDO = "undo";
    public static final String PROC_COMMAND_HIST = "hist";

    private InputStream inputStream;
    private OutputStream outputStream;
    private List<Float> data;
    Stack<List<Float>> dataStack;
    private boolean isStarted = false;

    public NumberProcessor(InputStream inputStream, OutputStream outputStream, List<Float> data) {
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
            case PROC_COMMAND_HIST:
                executeHistoryCommand();
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

    private void executeHistoryCommand() {
        dataStack.forEach((arg) -> {
            try {
                outputList(arg);
            } catch (IOException e) {

            }

        });
    }

    private void executeCalculatorCommand(Command command) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        saveStatus();
        Calculator.class.getMethod(command.name, List.class, Float.class).invoke(Calculator.class,
                data, command.value);
        outputData();
    }

    private void restoreStatus() {
        data = dataStack.pop();
    }

    private void saveStatus() {
        dataStack.push(new ArrayList<>(data));
    }

    // TODO consider command enum creation
    private Command parseInput(String input) {
        if (input.length() <= 1) {
            throw new RuntimeException("Invalid input");
        }
        if (input.equals(PROC_COMMAND_QUIT) || input.equals(PROC_COMMAND_UNDO) || input.equals(PROC_COMMAND_HIST)) {
            return new Command(input, 0f);
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

    private void outputData() throws IOException {
        outputList(data);
    }

    private void outputList(List<Float> arr) throws IOException {
        String formatForPartial = "%s";
        String formatForWhole = "%d";
        String result = arr
                .stream()
                .map(sourceDatum -> {
                    if (sourceDatum == (long) sourceDatum.floatValue()) {
                        return String.format(formatForWhole, (long) sourceDatum.floatValue());
                    } else {
                        return String.format(formatForPartial, sourceDatum);
                    }
                })
                .collect(Collectors.joining(";"));
        output(result);
    }

    private void output(String value) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.write(value);
        writer.newLine();
        writer.flush();
    }
}
