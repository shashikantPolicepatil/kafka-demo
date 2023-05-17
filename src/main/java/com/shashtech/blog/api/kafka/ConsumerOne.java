package com.shashtech.blog.api.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsumerOne {
	
	 private static final Logger LOGGER = LogManager.getLogger(ConsumerOne.class);
	
	public static void main(String[] a) {
			Properties properties = new Properties();
			properties.put("bootstrap.servers","http://localhost:9092");
			properties.put("group.id","quickstart-eventsGroup");
			properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
			properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
			properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
			properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
			properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer_one");		
			KafkaConsumer<String,String> consumer= new KafkaConsumer<>(properties);
			RebalanceListener rebalanceListener = new RebalanceListener(consumer);
			
			consumer.subscribe(Collections.singletonList("quickstart-events-four"),rebalanceListener);
			try {
			while(true) {
				ConsumerRecords<String,String> poll = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> consumerRecord : poll) {
					LOGGER.info("ConsumerOne:{} at offset:{} from partition:{}",consumerRecord.value(),
							consumerRecord.offset(),consumerRecord.partition());
					rebalanceListener.addOffset("quickstart-events-four", consumerRecord.partition(),consumerRecord.offset());
					System.out.println("");
				}
			}
			}catch(Exception ex) {
				LOGGER.error("Error from consumer one:{}",ex.getMessage());
			} finally{
				consumer.close();
			}

	}

}
