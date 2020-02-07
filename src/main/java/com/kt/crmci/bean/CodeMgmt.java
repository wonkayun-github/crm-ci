package com.kt.crmci.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CodeMgmt {
	
	@Id
    private String _id;
	private String codeGroupCd;
	private String codeCd;
	private String codeName;
}
