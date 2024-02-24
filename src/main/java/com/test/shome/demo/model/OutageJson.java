package com.test.shome.demo.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
@AllArgsConstructor
public class OutageJson{
    private String title;
    private String postalCodes;
    private String description;
    private String startDate;
    private String endDate;
    private String status;

    public JsonNode toJsonNode(ObjectMapper objectMapper) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("endDate", endDate.toString());
        node.put("title", title);
        node.put("postalCodes", postalCodes);
        node.put("status", status);
        node.put("startDate", startDate.toString());
        node.put("description", description);
        return node;
    }
}
