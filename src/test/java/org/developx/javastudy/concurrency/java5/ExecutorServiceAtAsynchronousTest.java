package org.developx.javastudy.concurrency.java5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ExecutorServiceAtAsynchronousTest {

    @Test
    void submit() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> "test";

        Future<String> submit = executorService.submit(callable);
        String ret = submit.get();

        assertThat(ret).isEqualTo("test");
        executorService.shutdown();
    }


    @Test
    void invokeAll() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Instant start = Instant.now();

        Callable<String> hello = () -> {
            Thread.sleep(1000L);
            final String result = "Hello";
            return result;
        };

        Callable<String> mang = () -> {
            Thread.sleep(4000L);
            final String result = "Java";
            return result;
        };

        Callable<String> kyu = () -> {
            Thread.sleep(2000L);
            final String result = "kyu";
            return result;
        };

        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, mang, kyu));
        for(Future<String> f : futures) {
            System.out.println(f.get());
        }

        System.out.println("time = " + Duration.between(start, Instant.now()).getSeconds());
        executorService.shutdown();
    }

    @Test
    void invokeAny() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Instant start = Instant.now();

        Callable<String> hello = () -> {
            Thread.sleep(1000L);
            final String result = "Hello";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> mang = () -> {
            Thread.sleep(4000L);
            final String result = "Java";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> kyu = () -> {
            Thread.sleep(2000L);
            final String result = "kyu";
            System.out.println("result = " + result);
            return result;
        };

        String result = executorService.invokeAny(Arrays.asList(hello, mang, kyu));
        System.out.println("result = " + result + " time = " + Duration.between(start, Instant.now()).getSeconds());

        executorService.shutdown();
    }

}
