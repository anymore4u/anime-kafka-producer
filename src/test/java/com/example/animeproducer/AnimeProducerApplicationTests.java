package com.example.animeproducer;

import com.example.animeproducer.service.AnimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AnimeProducerApplicationTests {

    @MockBean
    private AnimeService animeService;

    @Autowired
    private AnimeProducerApplication animeProducerApplication;

    @Test
    void mainApplicationStarts() {
        Assertions.assertDoesNotThrow(() -> {
            AnimeProducerApplication.main(new String[]{});
        });
    }

    @Test
    void runMethodExecutesSuccessfully() {
        Mockito.doNothing().when(animeService).fetchAndSendAnimeData();
        Assertions.assertDoesNotThrow(() -> {
            animeProducerApplication.run(animeService).run(new String[]{});
        });
    }

    @Test
    void runMethodHandlesException() {
        Mockito.doThrow(new RuntimeException("Test Exception")).when(animeService).fetchAndSendAnimeData();
        Assertions.assertThrows(RuntimeException.class, () -> {
            animeProducerApplication.run(animeService).run(new String[]{});
        });
    }
}