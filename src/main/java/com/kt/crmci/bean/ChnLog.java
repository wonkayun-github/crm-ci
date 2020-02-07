package com.kt.crmci.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ChnLog {

    @Id
    private String _id;
    private String created;
    private String createdBy;
	private String lastUpd;
	private String lastUpdBy;
	private String mstrId;
	private String custId;
	private String assetId;
	private String billAccntId;
	private String categoryLctgNm;
	private String categoryMctgNm;
	private String sndSystemNm;
	private String rcvrInfoSbst;
	private String otgorInfoSbst;
	private String infTmpltId;
	private String trmSbst;
	private String trmSttusCd;
	private String agntrDt;
	private String agntrFlg;
	private String rpeatExeTmscnt;
	private String srNo;
	private String rcvrSndDt;
}
