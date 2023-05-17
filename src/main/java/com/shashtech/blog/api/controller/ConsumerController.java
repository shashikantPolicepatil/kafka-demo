package com.shashtech.blog.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shashtech.blog.api.kafka.ConsumeMessage;
import com.shashtech.blog.api.kafka.ConsumeWithSyncAsyncCommit;

@RestController
public class ConsumerController {
	
	@Autowired
	private ConsumeMessage consumeMessage;
	
	@Autowired
	private ConsumeWithSyncAsyncCommit asyncCommit;
	
	
	@GetMapping("/one")
	public void pollOne() {
		consumeMessage.runOne();
	}
	
	@GetMapping("/two")
	public void pollTwo() {
		consumeMessage.runTwo();
	}

	@GetMapping("/three")
	public void pollthree() {
		consumeMessage.runthree();
	}
	
	@GetMapping("/asynccommit")
	public void pollWithCommit() {
		asyncCommit.commitSyncAsync();
	}
}
