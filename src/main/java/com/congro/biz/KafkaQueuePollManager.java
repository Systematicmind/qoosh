package com.congro.biz;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;

/**
 * Created by Amir on 12/18/2016.
 */
public class KafkaQueuePollManager<T> implements QueuePollManager<T> {

    private Consumer<String, byte[]> consumer;

    public KafkaQueuePollManager(String topicName, Properties producerConfig) {
        consumer = new KafkaConsumer<>(producerConfig);
        consumer.subscribe(new ArrayList<>(Arrays.asList(topicName)));
    }


    @PreDestroy
    protected void preDestroy() {
        closeQueue();
    }

    public List<T> readFromQueue(int pollWait) {
        List<T> readBuffer = new ArrayList<>();
        ConsumerRecords<String, byte[]> records = consumer.poll(pollWait);
        ByteArrayInputStream byteArrayInput;
        ObjectInputStream objectInputStream;
        for(ConsumerRecord<String, byte[]> consumerRecord : records) {
            byteArrayInput = new ByteArrayInputStream(consumerRecord.value());
            try {
                objectInputStream = new ObjectInputStream(byteArrayInput);
                T eventBody = (T) objectInputStream.readObject();
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

    @Override
    public void closeQueue() {
        consumer.close();
    }


//    public static void main(String[] args) {
//        EventBody eventBody = new EventBody();
//        eventBody.setEventType(5);
//        eventBody.setContentId("78599");
//        eventBody.setHostURL("http://mydomain.com");
//        KafkaQueueManager qoosh = new KafkaQueueManager("192.168.136.131:9092", "test2");
//        qoosh.initProducer();
//        qoosh.insertInQueue(eventBody);
//        qoosh.producer.flush();
//        qoosh.closeProducer();
//    }

//    public static void main(String[] args) {
//        KafkaQueuePollManager qoosh = new KafkaQueuePollManager("192.168.136.131:9092", "test2");
//        qoosh.initConsumer();
//        List<EventBody> eventBodies = qoosh.readFromQueue();
//        System.out.println(eventBodies.size());
//        qoosh.closeConsumer();
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
