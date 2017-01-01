package com.congro.biz;

import com.congro.data.EventBody;
import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by Amir on 12/18/2016.
 */
public class QueueManagerImpl implements QueueManager {

    private String bootstrapServer;
    private String topicName;

    Consumer<String, byte[]> consumer;
    Producer<String, byte[]> producer;

    public QueueManagerImpl(String bootstrapServer,String topicName) {
        this.bootstrapServer = bootstrapServer;
        this.topicName = topicName;
    }

    @PostConstruct
    protected void init() {
        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", bootstrapServer);
        consumerProps.put("group.id", "qoosh-group-2");
        consumerProps.put("enable.auto.commit", "false");
        consumerProps.put("auto.offset.reset", "earliest");
//        consumerProps.put("fetch.min.bytes","50000");
//        consumerProps.put("receive.buffer.bytes","262144");
//        consumerProps.put("max.partition.fetch.bytes","2097152");
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Arrays.asList(topicName));

        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", bootstrapServer);
        producerProps.put("acks", "all");
        producerProps.put("retries", 0);
        producerProps.put("batch.size", 8192); //8K per partition
        producerProps.put("linger.ms", 100);
        producerProps.put("buffer.memory", 16777216); //16M total size of the producer pool
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        producer = new KafkaProducer<>(producerProps);

    }

    @PreDestroy
    protected void preDestroy() {
        producer.close();
        consumer.close();
    }

    public void insertInQueue(EventBody eventBody) {
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

    public List<EventBody> readFromQueue() {
        List<EventBody> readBuffer = new ArrayList<>();
        ConsumerRecords<String, byte[]> records = consumer.poll(1000);
        ByteArrayInputStream byteArrayInput;
        ObjectInputStream objectInputStream;
        for(ConsumerRecord<String, byte[]> consumerRecord : records) {
            byteArrayInput = new ByteArrayInputStream(consumerRecord.value());
            try {
                objectInputStream = new ObjectInputStream(byteArrayInput);
                EventBody eventBody = (EventBody) objectInputStream.readObject();
                readBuffer.add(eventBody);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return readBuffer;
    }

    public void commitOffset() {
        consumer.commitSync();
    }

//    public static void main(String[] args) {
//        EventBody eventBody = new EventBody();
//        eventBody.setEventType(5);
//        eventBody.setContentId("78599");
//        eventBody.setHostURL("http://mydomain.com");
//        QueueManagerImpl qoosh = new QueueManagerImpl("192.168.136.131:9092", "test2");
//        qoosh.init();
//        qoosh.insertInQueue(eventBody);
//        qoosh.producer.flush();
//        qoosh.preDestroy();
//    }

//    public static void main(String[] args) {
//        QueueManagerImpl qoosh = new QueueManagerImpl("192.168.136.131:9092", "test2");
//        qoosh.init();
//        List<EventBody> eventBodies = qoosh.readFromQueue();
//        System.out.println(eventBodies.size());
//        qoosh.preDestroy();
//    }

//    public static void main(String[] args) {
//        EventBody eventBody = new EventBody();
//        eventBody.setEventType(5);
//        eventBody.setContentId("78599");
//        eventBody.setHostURL("http://mydomain.com");
//        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream("c:\\test.txt");
//            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayStream);
//            outputStream.writeObject(eventBody);
//            outputStream.flush();
//            byteArrayStream.writeTo(fileOutputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//
//        try {
//            ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("c:\\test.txt")));
//            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInput);
//            EventBody eventBody = (EventBody) objectInputStream.readObject();
//            System.out.println(eventBody);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
}
