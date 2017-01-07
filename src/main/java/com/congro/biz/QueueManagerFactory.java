package com.congro.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Amir Hajizadeh on 1/5/2017.
 */
@Component
public class QueueManagerFactory {

    public static final String KAFKA_QUEUE = "kafka";

    @Autowired
    private QueueConfigBuilder queueConfigBuilder;

    private QueueManagerFactory() {
    }

    public  <T> QueuePollManager<T> getPollQueue(String queue, String topicName) {
        if (KAFKA_QUEUE.equals(queue)) {
            return new KafkaQueuePollManager<>(topicName,
                    queueConfigBuilder.getConsumerConfig(MyConst.TOPIC_ONE_NAME));
        }
        throw new IllegalArgumentException(queue + " not found!");
    }

    public <T> QueuePushManager<T> getPushQueue(String queue, String topicName) {
        if (KAFKA_QUEUE.equals(queue)) {
            return new KafkaQueuePushManager<>(topicName,
                    queueConfigBuilder.getProducerConfig(MyConst.TOPIC_ONE_NAME));
        }
        throw new IllegalArgumentException(queue + " not found!");
    }

}
