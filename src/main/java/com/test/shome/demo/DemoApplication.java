package com.test.shome.demo;

import com.test.shome.demo.services.OutageConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan
@RestController
public class DemoApplication {

	private final OutageConversionService outageConversionService;

	@Autowired
	public DemoApplication(OutageConversionService outageConversionService) {
		this.outageConversionService = outageConversionService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostMapping("/business_outages")
	public ResponseEntity<String> getBusinessOutages(@RequestBody String xmlData) {
		try {
			String jsonData = outageConversionService.convertXmlToJsonForBusiness(xmlData);
			return ResponseEntity.ok(jsonData);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting XML to JSON");
		}
	}

	@PostMapping("/customer_outages")
	public ResponseEntity<String> getCustomerOutages(@RequestBody String xmlData) {
		try {
			String jsonData = outageConversionService.convertXmlToJsonForCustomer(xmlData);
			return ResponseEntity.ok(jsonData);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting XML to JSON");
		}
	}
}

