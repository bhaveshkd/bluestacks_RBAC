package com.bluestacks.utils;

public class UtilFunctions {

    public UtilFunctions() {}

    public void clearConsole() {
        try{
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                System.out.print("\033[H\033[2J");
                Runtime.getRuntime().exec("cls");
            }
            else {
                System.out.print("\033[H\033[2J");;
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e) {
            for (int i = 0; i < 100; i++){
                System.out.println("\n"); //stupid bypass for terminal
            }
        }
    }
}
