package com.Lesson5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestApp {
    static final int size = 10000000;
    static final int h = size / 2;

    public void tesSingleThread(){
        float[] arr=createArray();
        Long startTime= System.currentTimeMillis();
        for (int i = 0; i < size; i++)
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        Long endTime= System.currentTimeMillis();
        System.out.println("Time of working single-threading mode: "+(endTime-startTime)+" ms");
    }


    public void testMultiThread() {
        float[] arr=createArray();
        float[] arrPartOne= new float[h];
        float[] arrPartTwo= new float[h];

        Long startTime= System.currentTimeMillis();
        System.arraycopy(arr,0,arrPartOne,0,h);
        System.arraycopy(arr,h,arrPartTwo,0,h);

        Thread threadOne= new Thread(new Calculator(arrPartOne,h));
        Thread threadTwo= new Thread(new Calculator(arrPartTwo,h));
        threadOne.start();
        threadTwo.start();
        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arrPartOne,0,arr,0,h);
        System.arraycopy(arrPartTwo,0,arr,h,h);

        Long endTime= System.currentTimeMillis();
        System.out.println("Working time in two-threading mode: "+(endTime-startTime)+" ms");

    }

    public float[] createArray(){
        float[] testArray= new float[size];
        for (int i = 0; i < size; i++) {
            testArray[i]=1.0f;
        }
        return testArray;
    }


}
class Calculator implements Runnable{
    float[] arr;
    int capacity;

    public Calculator(float[] arr, int capacity) {
        this.arr = arr;
        this.capacity= capacity;
    }
    @Override
    public void run() {
        for (int i = 0; i < capacity; i++) {
            arr[i]=(float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
