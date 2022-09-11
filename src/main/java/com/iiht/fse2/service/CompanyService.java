package com.iiht.fse2.service;

import java.util.List;

import com.iiht.fse2.dto.CompanyDto;
import com.iiht.fse2.dto.CompanyListDto;
import com.iiht.fse2.model.Company;

public interface CompanyService {

	public Company registerCompany(CompanyDto companyDto);

	public List<CompanyListDto> getAllCompanyInfo();

	public CompanyDto getCompanyInfo(String companyCode);

	public boolean removeCompanyInfo(String companyCode);

}
