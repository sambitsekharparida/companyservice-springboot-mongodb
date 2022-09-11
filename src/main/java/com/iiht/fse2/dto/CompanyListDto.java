package com.iiht.fse2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CompanyListDto {

	private String companyName;

	private String stockPrice;
	
	private String code;
	
	private String ceo;
	
	private double turnOver;
	
	private String website;
	
	private String stockExchange;

}
