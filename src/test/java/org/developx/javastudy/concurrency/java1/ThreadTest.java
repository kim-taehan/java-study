package org.developx.javastudy.concurrency.java1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ThreadTest {

    @DisplayName("runnable interface 를 람다형태로 수행한다.")
    @Test
    void runnableTest(){
        // given
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
        runnable.run();
    }

    @DisplayName("가장 간단하게 tread 객체를 생성한다.")
    @Test
    void threadTest(){
        // given
        Thread thread = new MyThread();
        thread.start();
    }
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    @DisplayName("Thread 생성시 Runnable 인터페이스 활용한다.")
    @Test
    void threadTestByRunnable() {
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
        new Thread(runnable).start();
    }

}
