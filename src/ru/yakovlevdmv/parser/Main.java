package ru.yakovlevdmv.parser;

public class Main {

    public static void main(String[] args) {


        try {
            String filePath = (args[0]);
            Parser.parse(filePath);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error");
        }

    }
}
