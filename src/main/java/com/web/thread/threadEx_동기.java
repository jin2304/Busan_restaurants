package com.web.thread;


//동기
public class threadEx_동기 {

    //스레드 클래스
    static  class printThread extends Thread{

        @Override
        public void run(){
            for(int i=0; i<50; i++){
                System.out.printf("sub: %d\n", i+1);
            }
        }

    }



    public static void main(String[] args){


        //스레드 생성 및 실행
        printThread th = new printThread();
        th.start();


        for(int i=0; i<50; i++){
            System.out.printf("main: %d\n", i+1);
        }
    }
}
