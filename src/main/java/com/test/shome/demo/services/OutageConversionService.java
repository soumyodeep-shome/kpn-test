package com.test.shome.demo.services;

import org.springframework.stereotype.Service;

import static com.test.shome.demo.utils.XmlToJsonConverter.xmlToJsonParser;

@Service
public class OutageConversionService {

    public String convertXmlToJsonForBusiness(String xmlData) throws Exception {
        return xmlToJsonParser(xmlData, true);

    }


    public String convertXmlToJsonForCustomer(String xmlData) throws Exception {
        return xmlToJsonParser(xmlData, false);
    }
}

