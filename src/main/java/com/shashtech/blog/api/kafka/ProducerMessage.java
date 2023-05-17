package com.shashtech.blog.api.kafka;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProducerMessage {

	private static final Logger LOGGER = LogManager.getLogger(ProducerMessage.class);

	@Value("${kafka.topic}")
	private String topicName;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	public void sendMessageSync(String message) {
		LOGGER.info("meesage received to send:{}", message);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topicName, message);
		try {
			Future<RecordMetadata> send = kafkaProducer.send(producerRecord);
			RecordMetadata recordMetadata = send.get();
			LOGGER.info("meesage sent:offset:{}, partition:{}", recordMetadata.offset(), recordMetadata.partition());
		} catch (Exception ex) {
			LOGGER.info("Error while sending message:{}", ex.getMessage());
		}
	}

	public void sendMessage(String message) throws InterruptedException, ExecutionException {
		LOGGER.info("meesage received to send:{}", message);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topicName, message);
		 kafkaProducer.send(producerRecord);
		LOGGER.info("meesage sent");
	}

	public void sendMessageAsync(String message,Callback callback) throws InterruptedException, ExecutionException {
		LOGGER.info("meesage received to send:{}", message);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topicName, message);
		kafkaProducer.send(producerRecord, callback);
		LOGGER.info("meesage sent");
	}
}
