package com.bluestacks.utils;
import java.util.HashMap;
import java.util.Scanner;

public class UserPasswords {
    private HashMap<String, Integer> userPasswordsData;

    public UserPasswords(){
        this.userPasswordsData = new HashMap<String, Integer>();
    }

    public void addUserPassword(String userName, int passwordHash){
        this.userPasswordsData.put(userName, passwordHash);
    }

    public boolean verifyUser(String userName){
        System.out.println("Enter password : ");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        int passwordHash = option.hashCode();
        if (userPasswordsData.get(userName) == passwordHash) return true;
        return false;
    }
}
