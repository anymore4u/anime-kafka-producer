package com.example.animeproducer.web;

import com.example.animeproducer.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animes")
@RequiredArgsConstructor
public class AnimeController {

    private static final Logger logger = LoggerFactory.getLogger(AnimeController.class);

    private final AnimeService animeService;

    @GetMapping("/update")
    public String updateAnimes() {
        logger.info("Recebida requisição para atualização de animes");
        animeService.fetchAndSendAnimeData();
        logger.info("Atualização de animes concluída com sucesso");
        return "Anime data updated successfully.";
    }
}
