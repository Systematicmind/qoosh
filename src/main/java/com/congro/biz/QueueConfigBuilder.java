package com.congro.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by Amir Hajizadeh on 1/4/2017.
 */
@Component
public class QueueConfigBuilder {

    private static final String PRODUCER_PREFIX = "producer";
    private static final String CONSUMER_PREFIX = "consumer";
    private static final String SEPARATOR = ".";

    //    common properties
    private static final String BOOTSTRAP_SERVERS = "bootstrap.servers";

//    consumer properties
    private static final String GROUP_ID = "group.id";
    private static final String ENABLE_AUTO_COMMIT = "enable.auto.commit";
    private static final String AUTO_OFFSET_RESET = "auto.offset.reset";
    private static final String KEY_DESERIALIZER = "key.deserializer";
    private static final String VALUE_DESERIALIZER = "value.deserializer";
    private static final String THREAD_NO = "thread.no";
    private static final String THREAD_DELAY = "thread.delay";
    private static final String THREAD_INTERVAL = "thread.interval";
    private static final String POLL_WAIT = "poll.wait";

//    producer properties
    private static final String ACKS = "acks";
    private static final String RETRIES = "retries";
    private static final String BATCH_SIZE = "batch.size";
    private static final String LINGER_MS = "linger.ms";
    private static final String BUFFER_MEMORY = "buffer.memory";
    private static final String KEY_SERIALIZER = "key.serializer";
    private static final String VALUE_SERIALIZER = "value.serializer";

    @Autowired
    private Environment env;

    public Properties getConsumerConfig(String topicName) {
        Properties properties = new Properties();
        String preFix = getConsumerPrefix(topicName);
        properties.put(BOOTSTRAP_SERVERS,env.getProperty(preFix + BOOTSTRAP_SERVERS));
        properties.put(GROUP_ID,env.getProperty(preFix + GROUP_ID));
        properties.put(ENABLE_AUTO_COMMIT,env.getProperty(preFix + ENABLE_AUTO_COMMIT));
        properties.put(AUTO_OFFSET_RESET,env.getProperty(preFix + AUTO_OFFSET_RESET));
        properties.put(KEY_DESERIALIZER,env.getProperty(preFix + KEY_DESERIALIZER));
        properties.put(VALUE_DESERIALIZER,env.getProperty(preFix + VALUE_DESERIALIZER));
        return properties;
    }

    public Properties getProducerConfig(String topicName) {
        Properties properties = new Properties();
        String preFix = getProducerPrefix(topicName);
        properties.put(BOOTSTRAP_SERVERS,env.getProperty(preFix + BOOTSTRAP_SERVERS));
        properties.put(ACKS,env.getProperty(preFix + ACKS));
        properties.put(RETRIES,env.getProperty(preFix + RETRIES));
        properties.put(BATCH_SIZE,env.getProperty(preFix + BATCH_SIZE));
        properties.put(LINGER_MS,env.getProperty(preFix + LINGER_MS));
        properties.put(BUFFER_MEMORY,env.getProperty(preFix + BUFFER_MEMORY));
        properties.put(KEY_SERIALIZER,env.getProperty(preFix + KEY_SERIALIZER));
        properties.put(VALUE_SERIALIZER,env.getProperty(preFix + VALUE_SERIALIZER));
        return properties;
    }

    public short getConsumerThreadNumber(String topicName) {
        String number = env.getProperty(getConsumerPrefix(topicName) + THREAD_NO);
        return number == null ? (short)0 : Short.valueOf(number);
    }

    public short getConsumerThreadDelay(String topicName) {
        String delay = env.getProperty(getConsumerPrefix(topicName) + THREAD_DELAY);
        return delay == null ? (short)0 : Short.valueOf(delay);
    }

    public short getConsumerThreadInterval(String topicName) {
        String interval = env.getProperty(getConsumerPrefix(topicName) + THREAD_INTERVAL);
        return interval == null ? (short)0 : Short.valueOf(interval);
    }

    public int getConsumerPollWait(String topicName) {
        String delay = env.getProperty(getConsumerPrefix(topicName) + POLL_WAIT);
        return delay == null ? 0 : Integer.valueOf(delay);
    }

    protected String getConsumerPrefix(String topicName) {
        return CONSUMER_PREFIX + SEPARATOR + topicName + SEPARATOR;
    }

    protected String getProducerPrefix(String topicName) {
        return PRODUCER_PREFIX + SEPARATOR + topicName + SEPARATOR;
    }

}
