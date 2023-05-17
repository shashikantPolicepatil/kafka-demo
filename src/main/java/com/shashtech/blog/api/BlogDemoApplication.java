package com.shashtech.blog.api;

import java.time.LocalDate;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.shashtech.blog.api.kafka.ConsumeMessage;
import com.shashtech.blog.api.kafka.ProducerCallback;
import com.shashtech.blog.api.kafka.ProducerMessage;

@SpringBootApplication
public class BlogDemoApplication {
	
	  private static final Logger LOGGER = LogManager.getLogger(BlogDemoApplication.class);

	  
	  
	public static void main(String[] args) {
		LOGGER.info("Starting app...");
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BlogDemoApplication.class, args);
		configurableApplicationContext.getApplicationName();
		LOGGER.info("App is up and running...{}", configurableApplicationContext.getApplicationName());
		//LOGGER.error("App is up and running...{}", configurableApplicationContext.getApplicationName());
		try {
			run(configurableApplicationContext.getBean(ProducerMessage.class),
					configurableApplicationContext.getBean(ConsumeMessage.class));
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void run(ProducerMessage prodMes,ConsumeMessage consume) throws Exception {
		/*LOGGER.info("<<<send and forget>>>>>");
		for (int i = 0; i < 1000; i++) {
			prodMes.sendMessage("value:"+i);
		}
		
		LOGGER.info("<<<Send Sync>>>>>");
		
		for (int i = 0; i < 1000; i++) {
			prodMes.sendMessageSync("value:"+i);
		}
		*/
		Random random = new Random();
		StringBuilder str=new StringBuilder();
		LocalDate now = LocalDate.now();
		LOGGER.info("<<<Send Async>>>>>");
		while(true) {
		for (int i = 0; i < 100; i++) {
			str.append(now.getYear());
			str.append("-");
			str.append(now.getMonth().getValue());
			str.append("-").append(now.getDayOfMonth()).append(",")
			.append(random.nextInt(1000));
			prodMes.sendMessageAsync(str.toString(),new ProducerCallback(String.valueOf(str)));
			str.replace(0, str.length(), "");
		}
		now = now.plusDays(1);
		LOGGER.info("Date sent for {}-{}-{}",now.getYear(),now.getMonth().getValue(),now.getDayOfMonth());
		}
		
		//LOGGER.info("<<<<<Consume starts>>>>>>>");
		//consume.runAlways();
	}
	
	@Bean
	public KafkaProducer<String,String> getProducer() {
		Properties properties = new Properties();
		properties.put("bootstrap.servers","http://localhost:9092");
		properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
		/*properties.put("ack","org.apache.kafka.common.serialization.StringSerializer");
		properties.put("","org.apache.kafka.common.serialization.StringSerializer");
		properties.put("","org.apache.kafka.common.serialization.StringSerializer");*/
		
		return new  KafkaProducer<>(properties);
	}
	
	@Bean(name="one")
	public KafkaConsumer<String, String> getConsumer() {
		Properties properties = new Properties();
		properties.put("bootstrap.servers","http://localhost:9092");
		properties.put("group.id","quickstart-eventsGroup");
		properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer1");
		return new KafkaConsumer<>(properties);
	}
	
	@Bean(name="two")
	public KafkaConsumer<String, String> getConsumer2() {
		Properties properties = new Properties();
		properties.put("bootstrap.servers","http://localhost:9092");
		properties.put("group.id","quickstart-eventsGroup");
		properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer2");		
		return new KafkaConsumer<>(properties);
	}
	
	@Bean(name="three")
	public KafkaConsumer<String, String> getConsumer3() {
		Properties properties = new Properties();
		properties.put("bootstrap.servers","http://localhost:9092");
		properties.put("group.id","quickstart-eventsGroup");
		properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer3");		
		return new KafkaConsumer<>(properties);
	}
}
