package org.developx.javastudy.concurrency.java8;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CompletableFutureTest {

    /**
     * 비동기 작업 실행
     * runAsync: 반환값이 없는 경우 비동기로 작업 실행 콜, 함수형 인터페이스 Runnable 파라미터로 받음
     * supplyAsync: 반환값이 있는 경우 비동기로 작업 실행 콜, 함수형 인터페이스 Supplier 파라미터로 받음
     **/
    @Test
    void runAsync() throws ExecutionException, InterruptedException {
        Runnable runnable = () -> System.out.println("Thread: " + Thread.currentThread().getName());
        CompletableFuture<Void> future = CompletableFuture.runAsync(runnable);
        future.get();
        System.out.println("Thread: " + Thread.currentThread().getName());
    }

    @Test
    void supplyAsync() throws ExecutionException, InterruptedException {
        Supplier supplier = () -> "Thread: " + Thread.currentThread().getName();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);
        System.out.println(future.get());
        printThreadName("Thread");
    }

    private static void printThreadName(String prefix) {
        System.out.println( prefix + ": " + Thread.currentThread().getName());
    }

    /**
     * 작업 콜백
     * thenApply: 반환 값을 받아서 다른 값을 반환함, 함수형 인터페이스 function을 파라미터로 받음
     * thenAccpet: 반환 값을 받아 처리하고 값을 반환하지 않음, 함수형 인터페이스 Consumer를 파라미터로 받음
     * thenRun: 반환 값을 받지 않고 다른 작업을 실행함, 함수형 인터페이스 Runnable을 파라미터로 받음
     */
    @Test
    void thenApply() throws ExecutionException, InterruptedException {

        Supplier supplier = () -> "Thread: " + Thread.currentThread().getName();
        Function<String, String> function = ret -> ret.toUpperCase();

        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(supplier)
                .thenApply(function);
    }

    @Test
    void thenAccept() throws ExecutionException, InterruptedException {

        Supplier supplier = () -> "Thread: " + Thread.currentThread().getName();
        Consumer<String> consumer = ret -> System.out.println(ret.toUpperCase());

        CompletableFuture future = CompletableFuture.supplyAsync(supplier)
                .thenAccept(consumer);
    }

    @Test
    void thenRun() throws ExecutionException, InterruptedException {

        Supplier supplier = () -> "Thread: " + Thread.currentThread().getName();
        Runnable runnable = () -> System.out.println("Thread: " + Thread.currentThread().getName());
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(supplier)
                .thenRun(runnable);
    }

    /**
     * 작업 조합
     * thenCompose: 두 작업이 이어서 실행하도록 조합하며, 앞선 작업의 결과를 받아서 사용할 수 있음, 함수형 인터페이스 Function을 파라미터로 받음
     * thenCombine: 두 작업을 독립적으로 실행하고, 둘 다 완료되었을 때 콜백을 실행함, 함수형 인터페이스 Function을 파라미터로 받음
     * allOf: 여러 작업들을 동시에 실행하고, 모든 작업 결과에 콜백을 실행함
     * anyOf: 여러 작업들 중에서 가장 빨리 끝난 하나의 결과에 콜백을 실행함
     */
    @Test
    void thenCompose(){
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            return "hello java";
        });
        CompletableFuture<String> completableFuture = completableFuture1.thenCompose(this::afterProcess);
    }

    private CompletableFuture afterProcess(String message) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("후속프로세스 작업: "+ message);
        });
    }

    @Test
    void thenCombine(){
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            printThreadName("견우");
            return "견우";
        });
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            printThreadName("직녀");
            return "직녀";
        });

        CompletableFuture<String> future = completableFuture1.thenCombine(completableFuture2, (h, w) -> {
            printThreadName("thenCombine");
            return h + " " + w;
        });
    }

    @Test
    void allOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            printThreadName("견우");
            return "견우";
        });
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            printThreadName("직녀");
            return "직녀";
        });

        List<CompletableFuture<String>> futures = List.of(completableFuture1, completableFuture2);


        CompletableFuture<List<String>> result = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenApply(v -> futures.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.toList()));
        result.get().forEach(System.out::println);
    }

    @Test
    void anyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "견우";
        });
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            printThreadName("직녀");
            return "직녀";
        });

        CompletableFuture<Void> future = CompletableFuture.anyOf(completableFuture1, completableFuture2).thenAccept(System.out::println);
        future.get();
    }


}
