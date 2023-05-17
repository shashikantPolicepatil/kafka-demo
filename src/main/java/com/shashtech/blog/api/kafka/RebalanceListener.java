package com.shashtech.blog.api.kafka;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RebalanceListener implements ConsumerRebalanceListener {
	
	private static final Logger LOGGER = LogManager.getLogger(ConsumerRebalanceListener.class);
	
	private KafkaConsumer<String, String> consumer;
	
	Map<TopicPartition,OffsetAndMetadata> currentOffsets =  new HashMap<>();
	
	public RebalanceListener(KafkaConsumer<String,String> consumer) {
		this.consumer=consumer;
	}

	public void addOffset(String topic,int partition,long offset) {
		currentOffsets.put(new TopicPartition(topic, partition),new OffsetAndMetadata(offset));
	}
	
	public Map<TopicPartition,OffsetAndMetadata> getCurrentOffsets() {
		return this.currentOffsets; 
	}
	
	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		LOGGER.info("Following partitions are revoked");
		for (TopicPartition topicPartition : partitions) {
			LOGGER.info(topicPartition.partition());
		}
		LOGGER.info("Following partitions are committed..");
		for (TopicPartition topicPartition : currentOffsets.keySet()) {
			LOGGER.info(topicPartition.partition());
		}
		consumer.commitSync(currentOffsets);
		currentOffsets.clear();
	}

	@Override
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		LOGGER.info("Following partitions are assigned");
		for (TopicPartition topicPartition : partitions) {
			LOGGER.info(topicPartition.partition());
		}
	}

}
