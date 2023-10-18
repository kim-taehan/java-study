package org.developx.javastudy.concurrency.java5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ExecutorServiceAtLifecycleTest {
    @Test
    void shutdown() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Callable<String> callable = () -> "test";
        executorService.submit(callable);

        // shutdown 호출
        executorService.shutdown();

        // shutdown 호출 이후에는 새로운 작업들을 받을 수 없음, 에러 발생
        Assertions.assertThatThrownBy(() -> executorService.submit(callable))
                .isInstanceOf(RejectedExecutionException.class);
    }

    @Test
    void shutdownNow() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Start");
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted");
                    break;
                }
            }
            System.out.println("End");
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(runnable);

        executorService.shutdownNow();
        Thread.sleep(1000L);
    }


}
