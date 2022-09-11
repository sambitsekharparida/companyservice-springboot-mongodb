package com.iiht.fse2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.fse2.dto.CompanyDto;
import com.iiht.fse2.dto.CompanyListDto;
import com.iiht.fse2.model.Company;
import com.iiht.fse2.service.CompanyServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins ="http://localhost:3000")
@RestController
@RequestMapping(path = "/api/v1.0/company")
public class CompanyController {

	@Autowired
	private CompanyServiceImpl companyService;

	/**
	 * 'POST' request handler to save new company details
	 * @param companyName
	 * @param code
	 * @param ceo
	 * @param turnover
	 * @param website
	 * @param stockExchange
	 * @return Company
	 */
	@PostMapping(value = "/register")
	public Company registerCompany(@RequestBody CompanyDto companyDto) {
		log.info("Creating a new company and storing the details to DB");
		return companyService.registerCompany(companyDto);
	}

	/**
	 * 'GET' request handler to return all company details
	 * @return List<CompanyListDto>
	 */
	@GetMapping(value = "/getall")
	public List<CompanyListDto> getAllCompany() {
		log.info("Get all the company details");
		return companyService.getAllCompanyInfo();
	}

	/**
	 * 'GET' request handler to return a company information
	 * @param companyCode
	 * @return CompanyDto
	 */
	@GetMapping(value = "/info/{companycode}")
	public CompanyDto getCompanyInfo(@PathVariable("companycode") String companyCode) {
		log.info("Get Company Details");
		return companyService.getCompanyInfo(companyCode);
	}

	/**
	 * 'DELETE' request handler to remove a company information
	 * @param companyCode
	 * @return String
	 */
	@DeleteMapping("/delete/{companycode}")
	public ResponseEntity<Map<String, Boolean>> removeCompany(@PathVariable("companycode") String companyCode) {
		log.info("Remove the company details");
		Map<String, Boolean> response = new HashMap<>();
		boolean isDeleted = companyService.removeCompanyInfo(companyCode);
		response.put("deleted", isDeleted);
		return ResponseEntity.ok(response);
	}

}
