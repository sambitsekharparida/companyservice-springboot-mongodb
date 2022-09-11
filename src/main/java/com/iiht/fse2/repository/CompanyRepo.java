package com.iiht.fse2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.iiht.fse2.model.Company;

public interface CompanyRepo extends MongoRepository<Company, Long> {

	@Query("{ 'code': ?0}")
	public Company findCompanyInfo(String companyCode);

	@Query(value = "{'code' : ?0}", delete = true)
	public Company deleteCompany(String companyCode);

}
