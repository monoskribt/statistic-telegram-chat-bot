package com.statistictgchatbot.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class TextDeserialize extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if (node.isTextual()) {
            return node.asText();
        }

        if (node.isArray()) {
            StringBuilder sb = new StringBuilder();
            for (JsonNode n : node) {
                if (n.isTextual()) {
                    sb.append(n.asText());
                } else if (n.has("text") && n.get("text").isTextual()) {
                    sb.append(n.get("text").asText());
                }
            }
            return sb.toString();
        }

        return "";
    }
}
