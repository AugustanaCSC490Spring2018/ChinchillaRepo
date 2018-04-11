package com.example.chinchillas.chinchillachat;

import android.support.annotation.NonNull;

/**
 * Created by ivyvecna15 on 4/9/2018.
 */

public class PasswordAnalysis {

    public static final long NUM_ATTEMPTS_PER_SECOND = 7000000000L;
    public static final long NUM_SECONDS_PER_MINUTE = 60;
    public static final long NUM_SECONDS_PER_HOUR = NUM_SECONDS_PER_MINUTE * 60;
    public static final long NUM_SECONDS_PER_DAY = NUM_SECONDS_PER_HOUR * 24;
    public static final long NUM_SECONDS_PER_YEAR = NUM_SECONDS_PER_DAY * 365; // assume 365 days per year
    public static final long NUM_SECONDS_PER_DECADE = NUM_SECONDS_PER_YEAR * 10;
    public static final long NUM_SECONDS_PER_CENTURY = NUM_SECONDS_PER_DECADE * 10;
    public static final long NUM_SECONDS_PER_MILLENNIUM = NUM_SECONDS_PER_CENTURY * 10;


    public static void main(String[] args) {
        String pass1 = "test";
        String pass2 = "This should 12 be a STRONG password.";
        System.out.println(pass1);
        long pass1Time = passwordComplexity(pass1);
        System.out.println(pass1Time);
        System.out.println(timeTaken(pass1Time));
        System.out.println();
        System.out.println(pass2);
        long pass2Time = passwordComplexity(pass2);
        System.out.println(pass2Time);
        System.out.println(timeTaken(pass2Time));
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
        int numPossibilities = 0; // possibilities for each type of character
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
            numPossibilities += 26;
        }
        if (alphaUpper) {
            numPossibilities += 26;
        }
        if (num) {
            numPossibilities += 10;
        }
        if (symbol) {
            numPossibilities += 128 - 26 - 26 - 10;
        }
        if (ext) {
            numPossibilities += 256 - 128;
        }
        complexity = Math.pow(numPossibilities, length);
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
        int seconds = (int) (time % NUM_SECONDS_PER_MINUTE);
        int minutes = (int) (time % NUM_SECONDS_PER_HOUR / NUM_SECONDS_PER_MINUTE);
        int hours = (int) (time % NUM_SECONDS_PER_DAY / NUM_SECONDS_PER_HOUR);
        int days = (int) (time % NUM_SECONDS_PER_YEAR / NUM_SECONDS_PER_DAY);
        int years = (int) (time % NUM_SECONDS_PER_DECADE / NUM_SECONDS_PER_YEAR);
        int decades = (int) (time % NUM_SECONDS_PER_CENTURY / NUM_SECONDS_PER_DECADE);
        int centuries = (int) (time % NUM_SECONDS_PER_MILLENNIUM / NUM_SECONDS_PER_CENTURY);
        int millennia = (int) (time / NUM_SECONDS_PER_MILLENNIUM);

        StringBuilder sb = new StringBuilder();
        if (millennia > 0) {
            sb.append(millennia + " millennia ");
        }
        if (centuries > 0) {
            sb.append(centuries + " centuries ");
        }
        if (decades > 0) {
            sb.append(decades + " decades ");
        }
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