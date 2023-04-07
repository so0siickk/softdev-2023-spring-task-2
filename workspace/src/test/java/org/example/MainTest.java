package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    static private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    static private final PrintStream originalOut = System.out;

    @BeforeAll
    static public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    static public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void falseArgs() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String falseArgs[] = {"-c", "-w", "-o output.txt", "-r", "1-14"};
            Main.main(falseArgs);
        });
    }
    @Test
    void workWithFiles() throws IOException, CmdLineException {
        File expectedFile = new File("expectedFile.txt");
        String[] args = {"-w", "-o", "outputFile.txt", "-r", "2-5", "input/input.txt"};
        Main.main(args);
        File outputFile = new File("outputFile.txt");
        Scanner expectedScanner = new Scanner(expectedFile);
        Scanner answerScanner = new Scanner(outputFile);
        ArrayList<String> answer = new ArrayList<>();
        ArrayList<String> expected = new ArrayList<>();
        while (expectedScanner.hasNextLine()) {
            expected.add(expectedScanner.nextLine());
        }
        while (answerScanner.hasNextLine()) {
            answer.add(answerScanner.nextLine());
        }
        assertEquals(expected, answer);
    }

    @Test
    void workWithConsole() throws IOException, CmdLineException {
        String[] args = {"-c", "-r", "7-21", "vhkvvk", "fafafaf", "fsgsgs", "gsgsgsg", "gaggsg"};
        Main.main(args);
        String expectedAnswer = "fafafaf fsgsgs ";
        assertEquals(expectedAnswer,outContent.toString().replaceAll("\n",""));
    }
}
