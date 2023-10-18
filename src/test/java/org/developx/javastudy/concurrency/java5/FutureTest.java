package org.developx.javastudy.concurrency.java5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

public class FutureTest {

    private Callable<String> callable() {
        return () -> {
            Thread.sleep(3000L);
            return "Thread: " + Thread.currentThread().getName();
        };
    }

    @DisplayName("블로킹 방식으로 결과를 가져옴")
    @Test
    void get() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = callable();
        Future<String> future = executorService.submit(callable);
        String ret = future.get();
        executorService.shutdown();
    }

    @Test
    void isCancelled_False() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = callable();
        Future<String> future = executorService.submit(callable);

        assertThat(future.isCancelled()).isFalse();
        executorService.shutdown();
    }

    @Test
    void isCancelled_True() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = callable();
        Future<String> future = executorService.submit(callable);
        future.cancel(true);

        assertThat(future.isCancelled()).isTrue();
        executorService.shutdown();
    }

    @Test
    void isDone_False() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = callable();
        Future<String> future = executorService.submit(callable);

        assertThat(future.isDone()).isFalse();
        executorService.shutdown();
    }

    @Test
    void isDone_True() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = callable();
        Future<String> future = executorService.submit(callable);

        while (future.isDone()) {
            assertThat(future.isDone()).isTrue();
            executorService.shutdown();
        }

    }









}
