package com.example.chinchillas.chinchillachat;

import android.support.annotation.NonNull;

/**
 * Created by ivyvecna15 on 4/9/2018.
 */

public class PasswordAnalysis {

    public static final long NUM_ATTEMPTS_PER_SECOND = 7000000000L;

    public static void main(String[] args) {
        String pass1 = "test";
        String pass2 = "This should 12 be a STRONG password.";
        System.out.println(pass1);
        System.out.println(passwordComplexity(pass1));
        System.out.println();
        System.out.println(pass2);
        System.out.println(passwordComplexity(pass2));
    }

    /**
     * Computes an estimated number of seconds it will take to crack a password, given
     * a brute force attack making NUM_ATTEMPTS_PER_SECOND (7 billion) attempts per second.
     * This computation assumes brute force and does not take into account smarter attacks.
     *
     * @param password
     * @return number of seconds estimated to break the password
     */
    public static long passwordComplexity(String password) {
        double complexity = 0.0;
        int length = password.length();
        boolean alphaLower = false; // contains a lowercase letter
        boolean alphaUpper = false; // contains an uppercase letter
        boolean num = false; // contains a number
        boolean symbol = false; // contains a printable ASCII character
        boolean ext = false; // contains an extended character
        for (int i=0; i<length; i++) {
            if (alphaLower && alphaUpper && num && symbol && ext) { // all cases covered, no need to continue checking
                break;
            }
            char ch = password.charAt(i);
            if (ch >= 'a' && ch <= 'z') { // lowercase letter
                alphaLower = true;
            } else if (ch >= 'A' && ch <= 'Z') { // uppercase letter
                alphaUpper = true;
            } else if (ch >= '0' && ch <= '9') { // number
                num = true;
            } else if ((byte) ch >= 0 && (byte) ch <= 127) { // ASCII character
                symbol = true;
            }else {
                ext = true;
            }
        }

        if (alphaLower) {
            complexity += Math.pow(26, length);
        }
        if (alphaUpper) {
            complexity += Math.pow(26, length);
        }
        if (num) {
            complexity += Math.pow(10, length);
        }
        if (symbol) {
            complexity += Math.pow(128 - 26 - 26 - 10, length);
        }
        if (ext) {
            complexity += Math.pow(256 - 128, length);
        }
        return (long) (complexity / NUM_ATTEMPTS_PER_SECOND);
    }

    /**
     * Reformats a date in just seconds to years, days, hours, minutes, and seconds.
     *
     * @param time in seconds
     * @return a String representation of the amount of time
     */
    @NonNull
    public static String timeTaken (long time) {
        int seconds = (int) (time % 60);
        int minutes = (int) (time % (60 * 60) / 60);
        int hours = (int) (time % (60 * 60 * 24) / (60 * 60));
        int days = (int) (time % (60 * 60 * 24 * 365) / (60 * 60 * 24));
        int years = (int) (time / (60 * 60 * 24 * 365));

        StringBuilder sb = new StringBuilder();
        if (years > 0) {
            sb.append(years + " years ");
        }
        if (days > 0) {
            sb.append(days + " days ");
        }
        if (hours > 0) {
            sb.append(hours + " hours ");
        }
        if (minutes > 0) {
            sb.append(minutes + " minutes ");
        }
        if (seconds > 0) {
            sb.append(seconds + " seconds ");
        }
        return sb.toString();
    }
}