package com.Lesson5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestApp {
    static final int size = 10000000;
    static final int h = size / 2;

    public void testSingleThread(){
        float[] arr=createArray();
        Long startTime= System.currentTimeMillis();
        for (int i = 0; i < size; i++)
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        Long endTime= System.currentTimeMillis();
        System.out.println("Working time in single-threading mode: "+(endTime-startTime)+" ms");
    }


    public void testMultiThread() throws InterruptedException {
        float[] arr=createArray();
        float[][] arrPart= new float[2][h];
        Thread[] threadArr = new Thread[2];

        Long startTime= System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            System.arraycopy(arr, 0, arrPart[i], 0, h);
            threadArr[i]= new Thread(new Calculator(arrPart[i],h*i,h));
            threadArr[i].start();
        }
        for (int i = 0; i < 2; i++) {
            threadArr[i].join();
            System.arraycopy(arrPart[i],0,arr,h*i,h);
        }
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
    int start;
    public Calculator(float[] arr,int start, int capacity) {
        this.start=start;
        this.arr = arr;
        this.capacity= capacity;
    }
    @Override
    public void run() {
        for (int i = start; i < capacity; i++) {
            arr[i]=(float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
