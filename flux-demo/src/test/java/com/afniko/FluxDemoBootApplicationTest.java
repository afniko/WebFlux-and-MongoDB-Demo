package com.afniko;

import com.afniko.config.ContextTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = ContextTestConfig.class,
        webEnvironment = RANDOM_PORT)
class FluxDemoBootApplicationTest {

    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }

}
