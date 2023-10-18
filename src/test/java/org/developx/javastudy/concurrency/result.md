### 망나니개발자 블로그 참고한 내용
- [Thread와 Runnable에 대한 이해 및 사용법](https://mangkyu.tistory.com/258)
- [Callable, Future 및 Executors, Executor, ExecutorService, ScheduledExecutorService에 대한 이해 및 사용법](https://mangkyu.tistory.com/259)
- [CompletableFuture에 대한 이해 및 사용법](https://mangkyu.tistory.com/263)
 
#### 쓰레드 초보
```java
public class ThreadTest1 {

    @Test
    void threadTest() {
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
}
```


#### 쓰레드 중수 (리턴 값 처리 가능)
```java
public class ThreadTest2 {

    @Test
    void submit() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> "test";

        Future<String> submit = executorService.submit(callable);
        String ret = submit.get(); // 쓰레드 완료시까지 기다림

        assertThat(ret).isEqualTo("test");
        executorService.shutdown();
    }
}
```


#### 쓰레드 고수 (기다리지 않은 비동기 처리 가능)
```java
public class ThreadTest3 {
    @Test
    void thenApply() throws ExecutionException, InterruptedException {

        Supplier supplier = () -> "Thread: " + Thread.currentThread().getName();
        Function<String, String> function = ret -> ret.toUpperCase();

        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(supplier)
                .thenApply(function);
    }
}
```