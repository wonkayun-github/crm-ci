package com.kt.crmci.biz;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.crmci.bean.ChnLog;
import com.kt.crmci.bean.ChannelLogRepository;

@RestController
public class TestDateController {
	
	@Autowired
	ChannelLogRepository rep;
	
	private MongoOperations mongoOperation;
	
	@GetMapping(value="/test")
	public String getTest() {
		return "Success";
	}
	
	@GetMapping(value="/findAllTest")
	public List<ChnLog> findAllTest() {
		return rep.findAll();
	}
	
	@GetMapping(value="/findAllTestData")
	public Page<ChnLog> findAllTestData(Pageable pageable) {
		return rep.findAll(pageable);
	}
	
	@GetMapping(value="/findTestByCustId")
    public Page<ChnLog> findTestDataByCustId(@RequestParam String custId, Pageable pageable) {
        return rep.findByCustId(custId, pageable);
    }
	
	@GetMapping(value="/findTestByCategory")
    public Page<ChnLog> findTestByCategory(@RequestParam String category, Pageable pageable) {
		return rep.findByCategoryLctgNm(category, pageable);
    }
	
	@GetMapping(value="/findTestByCategorySms")
    public Page<ChnLog> findTestByCategorySms(Pageable pageable) {
		return rep.findByCategoryLctgNm("SMS", pageable);
    }
	
	@GetMapping(value="/findTestByCategoryFax")
    public Page<ChnLog> findTestByCategoryFax(Pageable pageable) {
		return rep.findByCategoryLctgNm("FAX", pageable);
    }
	
	@GetMapping(value="/findTestByCategoryEmail")
    public Page<ChnLog> findTestByCategoryEmail(Pageable pageable) {
		return rep.findByCategoryLctgNm("EMAIL", pageable);
    }

	@GetMapping(value="/findByCreatedBetween")
    public Page<ChnLog> findByCreatedBetween(@RequestParam String start, 
    		                                     @RequestParam String end, 
    		                                     Pageable pageable) {

		System.out.println(start);
		System.out.println(end);
		
        return rep.findByCreatedBetween(start, end, pageable);
    }
	
	@GetMapping(value="/findByCustIdAndCategoryLctgNm")
    public Page<ChnLog> findByCustIdAndCategoryLctgNm(@RequestParam String custId, 
    		                                              @RequestParam String category, 
    		                                              Pageable pageable) {
		System.out.println(custId);
		System.out.println(category);
		
        return rep.findByCustIdAndCategoryLctgNm(custId, category, pageable);
    }
	
	@GetMapping(value="/findByCreatedBetweenAndCustId")
    public Page<ChnLog> findByCreatedBetweenAndCustId(@RequestParam String start, 
    		                                              @RequestParam String end,
    		                                              @RequestParam String custId, 
    		                                              Pageable pageable) {

		System.out.println(start);
		System.out.println(end);
		System.out.println(custId);
		
        return rep.findByCreatedBetweenAndCustId(start, end, custId, pageable);
    }
	
	@GetMapping(value="/insertTest")
	public void insertTestData() {
		
		ChnLog data = new ChnLog();		
		System.out.println("_id : " + data.get_id());
		data.setCreated("2020-01-05 05:00:00");
		data.setCreatedBy("Tester");
		data.setLastUpd("2020-01-05 05:00:00");
		data.setMstrId("EMAIL_20140501122800_0-CH0000469436");
		data.setCustId("2-9999J5Y");
		data.setAssetId("1-10BW69R");
		data.setBillAccntId("");
		data.setCategoryLctgNm("SMS");
		data.setCategoryMctgNm("일반SMS");
		data.setSndSystemNm("KOS UI");
		data.setRcvrInfoSbst("1012341234");
		data.setOtgorInfoSbst("1022223333");
		data.setInfTmpltId("");
		data.setTrmSbst("[olleh알림]알 상품 추가충전 및 기타변경 안내");
		data.setTrmSttusCd("10000");
		data.setAgntrDt("");
		data.setAgntrFlg("");
		// data.setRpeatExeTmscnt(0);
		data.setRpeatExeTmscnt("");
		data.setSrNo("664939087");
		data.setRcvrSndDt("");
		
		rep.save(data);
	}
	
	@GetMapping(value="/updateTestMT")
	public void updateUseMongoTemplate() {

		Query query = new Query();
		query.addCriteria(Criteria.where("mstrId").is("MMS_201605042115_4-AYF4DK18910"));
		
		System.out.println("1");
		query.fields().include("mstrId");
		
		System.out.println("2");
		ChnLog chnLog = mongoOperation.findOne(query, ChnLog.class);
		System.out.println("chnLog - " + chnLog);

		Update update = new Update();
		update.set("trmSttusCd", "66666");
		
		mongoOperation.updateFirst(query, update, ChnLog.class);
		
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("mstrId").is("MMS_201605042115_4-AYF4DK18910"));
		ChnLog chnLog2 = mongoOperation.findOne(query2, ChnLog.class);
		System.out.println("chnLog2 - " + chnLog2);
		
		// mongoTemplate.updateFirst(query, update, ChnLog.class);
		// mongoTemplate.updateMulti(query, update, "chnLog");
	}
}
