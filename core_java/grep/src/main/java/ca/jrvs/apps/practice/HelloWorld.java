package ca.jrvs.apps.practice;

import java.util.Scanner;

class HelloWorld {

    // Your program begins with a call to main().
    // Prints "Hello, World" to the terminal window.
    public static void main(String[] args) {
        RegexExcImpl r = new RegexExcImpl();
        Scanner scnrin = new Scanner(System.in);
        String sin = scnrin.nextLine();

        System.out.println("match jp[e]g: " + r.matchJpeg(sin));
        System.out.println("match ip: " + r.matchIp(sin));
        System.out.println("is empty line: " + r.isEmptyLine(sin));
    }
}