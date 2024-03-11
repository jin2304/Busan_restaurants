package com.web.thread;


//동기식
public class threadEx3 {

    public static void main(String[] args){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                print();
            }
        };


        Thread th1 = new Thread(runnable);
        th1.start();


        for(int i=0; i<100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("main: %d\n", i + 1);
        }
    }


    private static void print() {

        for(int i=0; i<100; i++){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("sub: %d\n", i+1);
        }
    }





}
