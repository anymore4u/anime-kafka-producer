package com.example.animeproducer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AnimeProducerApplicationTests {

    @Test
    void mainApplicationStarts() {
        Assertions.assertDoesNotThrow(() -> {
            AnimeProducerApplication.main(new String[]{});
        });
    }
}
