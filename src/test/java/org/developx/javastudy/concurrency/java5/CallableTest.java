package org.developx.javastudy.concurrency.java5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

public class CallableTest {
    @DisplayName("callable simple test")
    @Test
    void callableTest() throws Exception {
        // given
        Callable<String> callable = () -> {
            return "hello callable";
        };
        // when
        String ret = callable.call();

        // then
        Assertions.assertThat(ret).isEqualTo("hello callable");
    }
}
