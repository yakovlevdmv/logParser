package ru.yakovlevdmv.parser;

import java.io.File;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {


        try {
            String filePath = (args[0]);
            System.out.println("file path: " + new File(filePath).getAbsolutePath());
            Parser.parse(filePath);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error");
        }

    }
}
