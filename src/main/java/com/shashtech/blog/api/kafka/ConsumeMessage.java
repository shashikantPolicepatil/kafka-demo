package com.shashtech.blog.api.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class ConsumeMessage {

	private static final Logger LOGGER = LogManager.getLogger(ConsumeMessage.class);

	@Value("${kafka.topic}")
	private String topicName;
	
	@Autowired
	@Qualifier(value="one")
	private KafkaConsumer<String, String> consumerOne;
	
	
	@Autowired
	@Qualifier(value="two")
	private KafkaConsumer<String, String> consumerTwo;
	
	@Autowired
	@Qualifier(value="three")
	private KafkaConsumer<String, String> consumerthree;
	
	public void runOne() {
		Collection<String> arrayList = new ArrayList<String>();
		arrayList.add(topicName);
		consumerOne.subscribe(arrayList);
		
		while(true) {
			ConsumerRecords<String,String> poll = consumerOne.poll(Duration.ofMillis(100));
			Iterator<ConsumerRecord<String, String>> iterator = poll.iterator();
			while(iterator.hasNext()) {
				ConsumerRecord<String,String> next = iterator.next();
				LOGGER.info("ConsumerOne:{} at offset:{} from partition:{}",next.value(),
						next.offset(),next.partition());
			}
			
		}
		
		
	}
	
	public void runTwo() {
		Collection<String> arrayList = new ArrayList<String>();
		arrayList.add(topicName);
		consumerTwo.subscribe(arrayList);
		
		while(true) {
			ConsumerRecords<String,String> poll = consumerTwo.poll(Duration.ofMillis(100));
			Iterator<ConsumerRecord<String, String>> iterator = poll.iterator();
			while(iterator.hasNext()) {
				ConsumerRecord<String,String> next = iterator.next();
				LOGGER.info("ConsumerTwo:{} at offset:{} from partition:{}",next.value(),
						next.offset(),next.partition());
			}
		}
	}
	
	public void runthree() {
		Collection<String> arrayList = new ArrayList<String>();
		arrayList.add(topicName);
		consumerthree.subscribe(arrayList);
		
		while(true) {
			ConsumerRecords<String,String> poll = consumerthree.poll(Duration.ofMillis(100));
			Iterator<ConsumerRecord<String, String>> iterator = poll.iterator();
			while(iterator.hasNext()) {
				ConsumerRecord<String,String> next = iterator.next();
				LOGGER.info("Consumerthree:{} at offset:{} from partition:{}",next.value(),
						next.offset(),next.partition());
			}
		}
	}
}
