package com.web.thread;


//비동기식
public class threadEx1 {

    public static void main(String[] args){
        print1();
        print2();

        for(int i=0; i<50; i++)
            System.out.printf("main: %d\n", i+1);
    }


    private static void print1() {
        for(int i=0; i<50; i++)
            System.out.printf("sub1: %d\n", i+1);
    }


    private static void print2() {
        for(int i=0; i<50; i++)
            System.out.printf("sub2: %d\n", i+1);
    }


}
