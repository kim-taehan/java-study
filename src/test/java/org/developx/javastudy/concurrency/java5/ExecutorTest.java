package org.developx.javastudy.concurrency.java5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

public class ExecutorTest {

    @DisplayName("Executor 기능 확인")
    @Test
    void test285(){
        // given
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName() + "=hello");
        Executor executor = command -> {
            command.run();
        };
        executor.execute(runnable);
    }

    @DisplayName("Executor 기능 확인시 runnable를 thread 로 다운 캐스팅")
    @Test
    void test282(){
        // given
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName() + "=hello");
        Executor executor = command -> {
            new Thread(command).start();
        };
        executor.execute(runnable);
    }
}
