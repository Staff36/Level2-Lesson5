package com.Lesson5;

public class Main {
    public static void main(String[] args) {
        TestApp testApp= new TestApp();
        testApp.testSingleThread();

        try {
            testApp.testMultiThread();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
