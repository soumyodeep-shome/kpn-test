package com.test.shome.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.test.shome.demo.services.OutageConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private OutageConversionService outageConversionService;

	@Test
	public void testCustomerOutageConversion() {
		ClassPathResource xmlResource = new ClassPathResource("outages.xml");
		ClassPathResource jsonResource = new ClassPathResource("customer_outages.json");
        String xmlData;
		String expectedJsonResult;
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode arrayNodeActual;
		ArrayNode arrayNodeExpected;
        try {
            xmlData = new String(FileCopyUtils.copyToByteArray(xmlResource.getInputStream()), StandardCharsets.UTF_8);
			expectedJsonResult = new String(FileCopyUtils.copyToByteArray(jsonResource.getInputStream()), StandardCharsets.UTF_8);
			arrayNodeExpected = (ArrayNode) objectMapper.readTree(expectedJsonResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String jsonResult;
        try {
            jsonResult = outageConversionService.convertXmlToJsonForCustomer(xmlData);
			arrayNodeActual = (ArrayNode) objectMapper.readTree(jsonResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

		assertEquals(arrayNodeExpected.size(), arrayNodeActual.size(), "Customer outage conversion passed");
	}

	@Test
	public void testBusinessOutageConversion() {
		ClassPathResource xmlResource = new ClassPathResource("outages.xml");
		ClassPathResource jsonResource = new ClassPathResource("business_outages.json");
		String xmlData;
		String expectedJsonResult;
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode arrayNodeActual;
		ArrayNode arrayNodeExpected;
		try {
			xmlData = new String(FileCopyUtils.copyToByteArray(xmlResource.getInputStream()), StandardCharsets.UTF_8);
			expectedJsonResult = new String(FileCopyUtils.copyToByteArray(jsonResource.getInputStream()), StandardCharsets.UTF_8);
			arrayNodeExpected = (ArrayNode) objectMapper.readTree(expectedJsonResult);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String jsonResult;
		try {
			jsonResult = outageConversionService.convertXmlToJsonForBusiness(xmlData);
			arrayNodeActual = (ArrayNode) objectMapper.readTree(jsonResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		assertEquals(arrayNodeExpected.size(), arrayNodeActual.size(), "Business outage conversion passed");
	}
}
