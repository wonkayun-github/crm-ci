package com.kt.crmci.bean;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChannelLogRepository extends MongoRepository<ChnLog, String> {
	
	Page<ChnLog> findAll(Pageable pageable);
	
	Optional<ChnLog> findByMstrId(String mstrId);
	
	Page<ChnLog> findByCustId(String custId, Pageable pageable);
	
	Page<ChnLog> findByCategoryLctgNm(String categoryLctgNm, Pageable pageable);
	
	Page<ChnLog> findByCustIdAndCategoryLctgNm(String custId, String categoryLctgNm, Pageable pageable);
	
	Page<ChnLog> findByCreatedBetween(String start, String end, Pageable pageable);
	
	Page<ChnLog> findByCreatedBetweenAndCustId(String start, String end, String custId, Pageable pageable);
	
	Page<ChnLog> findByCreatedBetweenAndCustIdAndCategoryLctgNm(String startTime, String endTime, String custId, String categoryLctgNm, Pageable pageable);	
}
