package com.shashtech.blog.api.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProducerCallback implements Callback {

	 private static final Logger LOGGER = LogManager.getLogger(ProducerCallback.class);
	 
	 private String message;
	 
	 public ProducerCallback(String message) {
		 this.message=message;
	}
	 
	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		if(exception!=null) {
			LOGGER.error("Failed while sending message:{}",exception.getMessage());
		} else {
			LOGGER.info("message sent Async value:{} Offerset:{},Partition:{}",this.message,metadata.offset(),metadata.partition());
		}
	}

}
