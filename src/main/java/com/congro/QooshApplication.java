package com.congro;

import com.congro.biz.EventTransferHub;
import com.congro.biz.QueueManager;
import com.congro.biz.QueueManagerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QooshApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(QooshApplication.class, args);
		EventTransferHub eventTransferHub = context.getBean(EventTransferHub.class);
		eventTransferHub.start();
	}

	@Bean
	public QueueManager queueManager() {
		return new QueueManagerImpl("192.168.136.131:9092","qoosh-2");
	}

}
