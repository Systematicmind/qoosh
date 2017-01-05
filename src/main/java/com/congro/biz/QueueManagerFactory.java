package com.congro.biz;

/**
 * Created by Amir Hajizadeh on 1/5/2017.
 */
public class QueueManagerFactory {

    public static final String KAFKA_QUEUE = "kafka";

    private QueueConfigBuilder queueConfigBuilder;

    private static QueueManagerFactory queueManagerFactory;

    private QueueManagerFactory() {
    }

    public static QueueManagerFactory getInstance() {
        if (queueManagerFactory == null) {
            queueManagerFactory = new QueueManagerFactory();
        }
        return queueManagerFactory;
    }

    public  <T> QueuePollManager<T> getQueue(String queue, String topicName) {
        if (KAFKA_QUEUE.equals(queue)) {
            return new KafkaQueuePollManager<>(topicName,
                    queueConfigBuilder.getConsumerConfig(MyConst.TOPIC_ONE_NAME));
        }
        throw new IllegalArgumentException(queue + " not found!");
    }

    public QueueConfigBuilder getQueueConfigBuilder() {
        return queueConfigBuilder;
    }

    public void setQueueConfigBuilder(QueueConfigBuilder queueConfigBuilder) {
        this.queueConfigBuilder = queueConfigBuilder;
    }
}
