package com.bluestacks;

public class Main {

    public static void main(String[] args) {
	// write your code here
        RBAManager manager = new RBAManager();
        boolean isRunning = true;
        while (isRunning){
            isRunning = manager.entryFunction();
        }
    }
}
