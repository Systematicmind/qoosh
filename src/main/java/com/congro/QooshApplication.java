package com.congro;

import com.congro.biz.EventTransferHub;
import com.congro.biz.QueueConfigBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@SpringBootApplication
@PropertySource("application.properties")
@PropertySource("kafkaproducer.properties")
@PropertySource("kafkaconsumer.properties")
public class QooshApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(QooshApplication.class, args);
		EventTransferHub eventTransferHub = context.getBean(EventTransferHub.class);
		eventTransferHub.run();
	}


}
