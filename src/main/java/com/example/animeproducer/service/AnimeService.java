package com.example.animeproducer.service;

import com.example.animeproducer.domain.Anime;
import com.example.animeproducer.domain.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.errors.TopicExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private static final Logger logger = LoggerFactory.getLogger(AnimeService.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaAdmin kafkaAdmin;
    final int delay = 2000;  // Delay de 2 segundos

    private void recreateTopic(String topicName) {
        Map<String, Object> config = new HashMap<>();
//        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:29092");

        try (AdminClient adminClient = AdminClient.create(config)) {
            Set<String> topics = adminClient.listTopics().names().get();
            if (topics.contains(topicName)) {
                adminClient.deleteTopics(Collections.singletonList(topicName)).all().get();
                logger.info("Tópico {} deletado com sucesso.", topicName);
            }
            adminClient.createTopics(Collections.singletonList(new NewTopic(topicName, 1, (short) 1))).all().get();
            logger.info("Tópico {} criado com sucesso.", topicName);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof TopicExistsException) {
                logger.warn("Tópico {} já existe.", topicName);
            } else {
                logger.error("Erro ao recriar o tópico {}: {}", topicName, e.getMessage());
            }
        } catch (InterruptedException e) {
            logger.error("Erro ao recriar o tópico {}: {}", topicName, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void cleanTopic(String topicName) {
        Map<String, Object> config = new HashMap<>();
//        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:29092");


        try (AdminClient adminClient = AdminClient.create(config)) {
            // Define `retention.ms` para 1 ms
            Map<String, String> newConfig = new HashMap<>();
            newConfig.put("retention.ms", "1");
            adminClient.alterConfigs(Collections.singletonMap(new ConfigResource(ConfigResource.Type.TOPIC, topicName),
                    new Config(Collections.singletonList(new ConfigEntry("retention.ms", "1"))))).all().get();

            logger.info("Tópico {} esvaziado.", topicName);

            // Redefine `retention.ms` para o valor original (pode ser o valor padrão ou um valor específico)
            newConfig.put("retention.ms", "604800000");  // Exemplo: 7 dias (em ms)
            adminClient.alterConfigs(Collections.singletonMap(new ConfigResource(ConfigResource.Type.TOPIC, topicName),
                    new Config(Collections.singletonList(new ConfigEntry("retention.ms", "604800000"))))).all().get();

            logger.info("Configuração do tópico {} redefinida.", topicName);

        } catch (ExecutionException | InterruptedException e) {
            logger.error("Erro ao esvaziar o tópico {}: {}", topicName, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    public void fetchAndSendAnimeData() {
        String apiUrl = "https://api.jikan.moe/v4/seasons/now";
        Set<Anime> uniqueAnimes = new HashSet<>();
        int currentPage = 1;

        logger.info("Iniciando a busca de dados de anime da API");

        // Recria os tópicos antes de enviar novos dados
/*        recreateTopic("anime-topic");
        recreateTopic("control-topic");*/

        // Limpa os tópicos antes de enviar novos dados
/*        cleanTopic("anime-topic");
        cleanTopic("control-topic");*/

        boolean hasNextPage;
        do {
            String pageUrl = apiUrl + "?page=" + currentPage + "&sfw";
            ApiResponse response = restTemplate.getForObject(pageUrl, ApiResponse.class);

            if (response != null && response.getData() != null) {
                uniqueAnimes.addAll(response.getData());
                hasNextPage = response.getPagination().isHasNextPage();
                currentPage++;
            } else {
                hasNextPage = false;
            }

            // Adiciona um delay entre as requisições
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Erro durante o delay entre as requisições", e);
            }
        } while (hasNextPage);

        logger.info("Enviando {} animes para o Kafka", uniqueAnimes.size());
        uniqueAnimes.forEach(anime -> {
            if (anime.getScore() > 0.0 && anime.getStatus().equals("Currently Airing")) {
                kafkaTemplate.send("anime-topic", String.valueOf(anime.getMalId()), anime);
            }
        });

        // Enviar mensagem de controle para sinalizar o final da produção
        kafkaTemplate.send("control-topic", "end-of-production", "true");

        logger.info("Processo de busca e envio de dados de animes concluído.");
    }
}
