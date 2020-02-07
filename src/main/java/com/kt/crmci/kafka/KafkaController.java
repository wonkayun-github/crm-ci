package com.kt.crmci.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kt.crmci.bean.ChnLog;

@RestController
public class KafkaController {
	
	@Autowired
	private KafkaProvider kafkaProvider;
	
	@GetMapping("/sendKafka")
	public void sendKafkaTest() {

		String strTest = "Test Text";
		kafkaProvider.sendMessage(strTest);
	}
	
	@PostMapping("/sendKafkaChnLog")
	public void sendKafkaChnLog(@RequestBody ChnLog body) {
		kafkaProvider.sendMessageChnLog(body);
	}
}
