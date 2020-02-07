package com.kt.crmci.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Customer {
	
	@Id
    private String _id;
	private String custId;
	private String custName;
}
