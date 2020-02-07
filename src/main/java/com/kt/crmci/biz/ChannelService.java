package com.kt.crmci.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.kt.crmci.bean.ChnLog;
import com.kt.crmci.bean.CodeMgmt;
import com.kt.crmci.bean.CodeMgmtRepository;
import com.kt.crmci.bean.ChannelLogRepository;

@Service
public class ChannelService {
	
	@Autowired
    private ChannelLogRepository channelLogRepository;
	
	@Autowired
	private CodeMgmtRepository codeMgmtRepository;
	
	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;
	
	private String trmSttusCd = null;

	public Page<ChnLog> findChannelHistory(String start, String end, String custId, String category, Pageable pageable) {
		return channelLogRepository.findByCreatedBetweenAndCustIdAndCategoryLctgNm(start, end, custId, category, pageable);		
	}
	
    public ChnLog findChannelHistoryDetail(String _id, Pageable pageable) {
    	
    	Optional<ChnLog> cl = channelLogRepository.findById(_id);
        trmSttusCd = cl.get().getTrmSttusCd();

        CodeMgmt cmi = codeMgmtRepository.findByCodeCd(trmSttusCd);
        
        if(cmi == null) {
        	cl.get().setTrmSttusCd(trmSttusCd + " : 알 수 없는 오류(기준정보 미등록)");
        } else {
        	cl.get().setTrmSttusCd(trmSttusCd + " : " + cmi.getCodeName());
        }
        
        return cl.get();
    }
    
    public void saveChannelLog(ChnLog body) {
    	channelLogRepository.save(body);  	
    }
    
    public void saveChannelLogKafka(ChnLog body) {

    	channelLogRepository.save(body);
    	this.sendChannelMessage(body);    	
    }
    
    public void sendChannelMessage(ChnLog body) {

    	String topicNm;
    	String categoryLctgNm = body.getCategoryLctgNm();
    	    	
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

	public void updateSendResult(String mstrId, String trmSttusCd) {

		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String strTime = format.format(time);
		
		System.out.println(strTime + "Input  mstrId: " + mstrId + "  trmSttusCd: " + trmSttusCd);
		
		Optional<ChnLog> cl = channelLogRepository.findByMstrId(mstrId);
		ChnLog chnlog = cl.get();
		chnlog.setTrmSttusCd(trmSttusCd);
		
		channelLogRepository.save(chnlog);
	}
}
