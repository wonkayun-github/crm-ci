package com.kt.crmci.bean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeMgmtRepository extends MongoRepository<CodeMgmt, String> {
	
	Page<CodeMgmt> findByCodeCd(String trmSttusCd, Pageable pageable);
	
	CodeMgmt findByCodeCd(String trmSttusCd);

}
