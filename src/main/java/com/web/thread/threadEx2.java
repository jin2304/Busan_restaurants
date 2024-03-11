package com.web.thread;


//동기식
public class threadEx2 {

    public static void main(String[] args){

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                print1();
            }
        });

        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                print2();
            }
        });


        th1.start();
        th2.start();



        for(int i=0; i<50; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("main: %d\n", i+1);
        }
    }


    private static void print1() {

        for(int i=0; i<50; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("sub1: %d\n", i+1);
        }
    }



    private static void print2() {
        for(int i=0; i<50; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("sub2: %d\n", i+1);
        }
    }





}
