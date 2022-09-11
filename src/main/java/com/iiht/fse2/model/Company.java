package com.iiht.fse2.model;

//import javax.persistence.Id;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Company")
public class Company {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	//@Id
	private long id;

	private String companyName;

	private String code;

	private String ceo;

	private double turnOver;

	private String website;

	private String stockExchange;

}
