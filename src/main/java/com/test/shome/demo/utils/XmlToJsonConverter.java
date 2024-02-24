package com.test.shome.demo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.shome.demo.model.OutageJson;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class XmlToJsonConverter {

    // This was just used to test the logic in a simple way also to read and write to file system
    public static void main(String[] args) {
        // KPN :: You can change the path according to your needs
        String xmlFilePath = "/Users/soumyodeepshome/Documents/workspace/outages.xml";

        String businessJsonFilePath = "/Users/soumyodeepshome/Documents/workspace/business.json";
        String customerJsonFilePath = "/Users/soumyodeepshome/Documents/workspace/customer.json";

        try {
            ObjectMapper xmlMapper = new XmlMapper();
            ObjectMapper jsonMapper = new ObjectMapper();
            ArrayNode businessArray = jsonMapper.createArrayNode();
            ArrayNode customerArray = jsonMapper.createArrayNode();

            JsonNode rootNode = xmlMapper.readTree(new File(xmlFilePath));

            rootNode.at("/channel/item").forEach(outageNode -> {
                try {
                    String locations = outageNode.path("locations").asText();
                    JsonNode convertedOutage;
                    if ((locations.equalsIgnoreCase("ZMST;") || locations.equalsIgnoreCase("ZMOH;"))) {
                        convertedOutage = convertOutage(outageNode);
                        businessArray.add(convertedOutage);
                    } else {
                        convertedOutage = convertOutage(outageNode);
                        customerArray.add(convertedOutage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            jsonMapper.writerWithDefaultPrettyPrinter().writeValue(new File(businessJsonFilePath), businessArray);
            jsonMapper.writerWithDefaultPrettyPrinter().writeValue(new File(customerJsonFilePath), businessArray);

            System.out.println("Conversion completed successfully.");
        } catch (IOException e) {
            System.err.println("Error converting XML to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String xmlToJsonParser(String xmlString, Boolean isBusiness) {
        ObjectMapper xmlMapper = new XmlMapper();
        ObjectMapper jsonMapper = new ObjectMapper();
        ArrayNode arrayNodes = jsonMapper.createArrayNode();
        String arrayAsString = "";
        try {
            JsonNode rootNode = xmlMapper.readTree(xmlString);
            if (isBusiness) {
                rootNode.at("/channel/item").forEach(outageNode -> {
                    String locations = outageNode.path("locations").asText();
                    JsonNode convertedOutage;
                    if ((locations.equalsIgnoreCase("ZMST;") || locations.equalsIgnoreCase("ZMOH;"))) {
                        convertedOutage = convertOutage(outageNode);
                        arrayNodes.add(convertedOutage);
                    }
                });
            } else {
                rootNode.at("/channel/item").forEach(outageNode -> {
                    String locations = outageNode.path("locations").asText();
                    JsonNode convertedOutage;
                    if (!((locations.equalsIgnoreCase("ZMST;") || locations.equalsIgnoreCase("ZMOH;")))) {
                        convertedOutage = convertOutage(outageNode);
                        arrayNodes.add(convertedOutage);
                    }
                });
            }
            jsonMapper.disable(SerializationFeature.INDENT_OUTPUT);
            arrayAsString = jsonMapper.writeValueAsString(arrayNodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayAsString;
    }

    private static JsonNode convertOutage(JsonNode outageNode) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        String title = outageNode.path("title").asText();
        String postalCodes = outageNode.path("postalCodes").asText();
        String description = outageNode.path("description").asText();
        List<String> times = Util.extractTimes(description);
        String startDateStr = times.get(0);
        String endDateStr = times.get(1);

        String status = "";
        try {
            Date currentDate = new Date();
            if (endDateStr.equalsIgnoreCase("onbekend") || dateFormat.parse(endDateStr).after(currentDate)) {
                status = "Actueel";
            } else if (!startDateStr.equalsIgnoreCase("onbekend") && dateFormat.parse(startDateStr).after(currentDate)) {
                status = "Gepland";
            } else if (dateFormat.parse(endDateStr).before(currentDate)) {
                status = "Opgelost";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new OutageJson(title, postalCodes, description, startDateStr, endDateStr, status).toJsonNode(new ObjectMapper());
    }



}

