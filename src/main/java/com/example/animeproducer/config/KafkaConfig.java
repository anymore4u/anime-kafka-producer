package com.example.animeproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic animeTopic() {
        return TopicBuilder.name("anime-topic")
                .partitions(1)
                .replicas(1)
                //.config("cleanup.policy", "delete")
                .build();
    }

    @Bean
    public NewTopic controlTopic() {
        return TopicBuilder.name("control-topic")
                .partitions(1)
                .replicas(1)
                //.config("cleanup.policy", "delete")
                .build();
    }
}
