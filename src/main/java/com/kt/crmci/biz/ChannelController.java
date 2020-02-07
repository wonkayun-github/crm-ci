package com.kt.crmci.biz;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.crmci.bean.ChnLog;
import com.kt.crmci.bean.updateChnLog;

@RestController
@RequestMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class ChannelController {
	
	private ChannelService channelService;
    
    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }
    
    @GetMapping("/findChannelHistory")
    public Page<ChnLog> findChannelHistory(@RequestParam String custId, 
    		                                   @RequestParam String start,
                                               @RequestParam String end,
                                               @RequestParam String category,
    		                                   Pageable pageable) {

        return channelService.findChannelHistory(start, end, custId, category, pageable);
    }
    
    @GetMapping("/findChannelHistoryDetail")
    public ChnLog findChannelHistoryDetail(@RequestParam String _id, Pageable pageable) {
        return channelService.findChannelHistoryDetail(_id, pageable);
    }
    
    @PostMapping("/saveChannelLog")
    public void saveChannelLog(@RequestBody ChnLog body) {
        channelService.saveChannelLog(body);
    }
    
    @PostMapping("/saveChannelLogKafka")
    public void saveChannelLogKafka(@RequestBody ChnLog body) {
        channelService.saveChannelLogKafka(body);
    }
    
    @PutMapping("/updateSendResult")
    public void updateSendResult(@RequestBody updateChnLog body) {
    	channelService.updateSendResult(body.getMstrId(), body.getTrmSttusCd());
    }
}
