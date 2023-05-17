package com.shashtech.blog.api.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

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
public class ConsumeWithSyncAsyncCommit {

	private static final Logger LOGGER = LogManager.getLogger(ConsumeWithSyncAsyncCommit.class);

	@Value("${kafka.topic}")
	private String topicName;
	
	@Autowired
	@Qualifier(value="one")
	private KafkaConsumer<String, String> consumerOne;
	
	
	public void commitSyncAsync() {
		Collection<String> arrayList = new ArrayList<String>();
		arrayList.add(topicName);
		consumerOne.subscribe(arrayList);
		
		try {
			while(true) {
				ConsumerRecords<String,String> poll = consumerOne.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> consumerRecord : poll) {
					LOGGER.info("ConsumerOne:{} at offset:{} from partition:{}",consumerRecord.value(),
							consumerRecord.offset(),consumerRecord.partition());
				}
				consumerOne.commitAsync();
				if(poll.count()>1)
					break;
				
			}
		} catch(Exception ex) {
			LOGGER.error("Error while consuming:{}",ex.getMessage());
		} finally {
			consumerOne.commitSync();
			//consumerOne.close();
		}
		
	}
}
