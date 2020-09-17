package com.mobarak.todo.utility;

public class Utility {

    public static boolean isNullOrEmpty(String text) {
        return text == null || text.trim().length() <= 0;
    }
}
