package com.congro.biz;

import com.congro.data.EventBody;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.PreDestroy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Properties;

/**
 * Created by Amir Hajizadeh on 1/4/2017.
 */
public class KafkaQueuePushManager<T> implements QueuePushManager<T> {

    Producer<String, byte[]> producer;
    private String topicName;

    public KafkaQueuePushManager(String topicName, Properties producerConfig) {
        this.topicName = topicName;
        producer = new KafkaProducer<>(producerConfig);
    }

    public void insertInQueue(T eventBody) {
        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayStream);
            outputStream.writeObject(eventBody);
            outputStream.flush();
            producer.send(new ProducerRecord<>(topicName, byteArrayStream.toByteArray()));
            producer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void closeProducer() {
        producer.close();
    }

    @PreDestroy
    protected void preDestroy() {
        closeProducer();
    }

}
