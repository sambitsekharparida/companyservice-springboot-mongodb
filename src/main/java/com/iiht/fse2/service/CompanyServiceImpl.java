package com.iiht.fse2.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.iiht.fse2.dto.CompanyDto;
import com.iiht.fse2.dto.CompanyListDto;
import com.iiht.fse2.model.Company;
import com.iiht.fse2.repository.CompanyRepo;

@Service
@Component
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepo companyRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private SequenceGeneratorService sequenceGenerator;
	
	@Value("${STOCK_SERVICE_URI}")
	String stockServiceUri;

	@Override
	public Company registerCompany(CompanyDto companyDto) {

		Company company = new Company();
		company.setId(sequenceGenerator.generateSequence(Company.SEQUENCE_NAME));
		company.setCompanyName(companyDto.getCompanyName());
		company.setCode(companyDto.getCode());
		company.setCeo(companyDto.getCeo());
		company.setTurnOver(companyDto.getTurnOver());
		company.setWebsite(companyDto.getWebsite());
		company.setStockExchange(companyDto.getStockExchange().toUpperCase());

		return companyRepo.save(company);
	}

	@Override
	public List<CompanyListDto> getAllCompanyInfo() {

		List<CompanyListDto> companyListDto = new ArrayList<>();
		List<Company> companyList = companyRepo.findAll();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		for (Company company : companyList) {

			String price = restTemplate.exchange(stockServiceUri + "stockPrice/" + company.getCode(),
					HttpMethod.GET, entity, String.class).getBody();

			companyListDto.add(new CompanyListDto()
					.setCompanyName(company.getCompanyName())
					.setStockPrice(price)
					.setCeo(company.getCeo())
					.setStockExchange(company.getStockExchange())
					.setWebsite(company.getWebsite())
					.setCode(company.getCode())
					.setTurnOver(company.getTurnOver()));
		}

		return companyListDto;
	}

	@Override
	public CompanyDto getCompanyInfo(String companyCode) {

		CompanyDto data = new CompanyDto();
		Company company = null;
		company = companyRepo.findCompanyInfo(companyCode);

		if (company != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>(headers);

			String price = restTemplate
					.exchange(stockServiceUri + "stockPrice/" + companyCode, HttpMethod.GET, entity, String.class)
					.getBody();

			data.setCeo(company.getCeo()).setCode(companyCode).setCompanyName(company.getCompanyName())
					.setStockExchange(company.getStockExchange()).setStockPrice(price)
					.setTurnOver(company.getTurnOver()).setWebsite(company.getWebsite());
		}

		return data;
	}

	@Override
	public boolean removeCompanyInfo(String companyCode) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Company cmpny = companyRepo.deleteCompany(companyCode);

		if (cmpny != null) {
			restTemplate
					.exchange(stockServiceUri + "removeStock/" + companyCode, HttpMethod.DELETE, entity, String.class)
					.getBody();
		}
		
		return cmpny != null ? true : false;
	}
	
}
