package com.web.thread;


//비동기
public class threadEx {

    //스레드 클래스
    static  class printThread extends Thread{

        @Override
        public void run(){
            for(int i=0; i<50; i++){
                try {
                    Thread.sleep(100); //스레드가 100ms 동안 잠을 잔다, 한 스레드가 잠을 자면 JVM은 다른 스레드를 실행
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("sub: %d\n", i+1);
            }
        }

    }



    public static void main(String[] args){


        //스레드 생성 및 실행
        printThread th = new printThread();
        th.start();


        for(int i=0; i<50; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("main: %d\n", i+1);
        }
    }
}
