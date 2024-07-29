package com.example.animeproducer;

import com.example.animeproducer.service.AnimeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnimeProducerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnimeProducerApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(AnimeService animeService) {
		return args -> {
			animeService.fetchAndSendAnimeData();
			System.exit(0);
		};
	}
}
