package org.torento.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.torento.model.ProductProfileProcessMetadata;

@KafkaClient
public interface SendToPersistenceLayer {
    @Topic(KafkaTopics.SAVE_TO_PERSISTENCE_LAYER_TOPIC)
    void sendMessage(@KafkaKey String key, ProductProfileProcessMetadata listProductProfileProcessMetadata);
}
