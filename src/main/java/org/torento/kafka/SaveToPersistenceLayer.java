package org.torento.kafka;

import io.micronaut.configuration.kafka.annotation.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torento.dao.SaveProductProfileDao;
import org.torento.model.ProductProfileProcessMetadata;

import javax.inject.Inject;
import java.util.Collections;

@KafkaListener(offsetReset = OffsetReset.EARLIEST, offsetStrategy = OffsetStrategy.SYNC)
public class SaveToPersistenceLayer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveToPersistenceLayer.class);

    @Inject
    private SaveProductProfileDao saveProductProfileDao;

    @Topic(KafkaTopics.SAVE_TO_PERSISTENCE_LAYER_TOPIC)
    public void receive(@KafkaKey String key, ProductProfileProcessMetadata productProfileProcessMetadata, long offset,
                        int partition,
                        String topic,
                        Consumer kafkaConsumer){
        LOGGER.info("*** Received Message ***");
        saveProductProfileDao.addProductProfile(productProfileProcessMetadata);
        //kafkaConsumer.commitSync();
        kafkaConsumer.commitSync(Collections.singletonMap(new TopicPartition(topic, partition),
                new OffsetAndMetadata(offset + 1, "my metadata")));
        LOGGER.info("Message {}, read from topic {}.",productProfileProcessMetadata,KafkaTopics.SAVE_TO_PERSISTENCE_LAYER_TOPIC);
    }
}
