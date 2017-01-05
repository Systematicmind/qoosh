package com.congro;

import com.congro.biz.EventTransferHub;
import com.congro.biz.QueuePollManager;
import com.congro.biz.KafkaQueuePollManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SpringBootApplication
@PropertySource("application.proprties")
@PropertySource("kafkaproducer.proprties")
@PropertySource("kafkaconsumer.proprties")
public class QooshApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(QooshApplication.class, args);
		EventTransferHub eventTransferHub = context.getBean(EventTransferHub.class);
		eventTransferHub.run();
	}


}
