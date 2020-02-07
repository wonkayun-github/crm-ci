package com.kt.crmci.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.kt.crmci.bean.ChnLog;

@Service
public class KafkaProvider {
	
	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;
	
	public void sendMessage(String strText) {
		this.kafkaTemplate.send("topictest", strText);
	}
	
	public void sendMessageChnLog(ChnLog body) {
		
    	String topicNm;
    	String categoryLctgNm = body.getCategoryLctgNm();
    	
    	System.out.println("categoryLctgNm : " + categoryLctgNm );
    	
    	if(categoryLctgNm.equals("SMS") || categoryLctgNm.equals("MMS")) {
    		topicNm = "smsTopic";

    	} else if(categoryLctgNm.equals("EMAIL")) {
    		topicNm = "emailTopic";
    		
    	} else if(categoryLctgNm.equals("FAX")) {
    		topicNm = "faxTopic";
    		
    	} else {
    		topicNm = "etcTopic";
    		
    	}

    	System.out.println("categoryLctgNm : " + categoryLctgNm );
		System.out.println("send topic to : " + topicNm );
		
		ListenableFuture<SendResult<Object, Object>> future = kafkaTemplate.send(topicNm, body);

        future.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {

            @Override
            public void onSuccess(SendResult<Object, Object> result) {
            	System.out.println("SUCCESS / " + categoryLctgNm + " / " + topicNm );
            }

            @Override
            public void onFailure(Throwable ex) {
            	System.out.println("FAIL    / " + categoryLctgNm + " / " + topicNm );
            }
        });
	}
}
