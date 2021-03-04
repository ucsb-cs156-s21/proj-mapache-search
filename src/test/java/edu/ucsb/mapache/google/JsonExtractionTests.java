package edu.ucsb.mapache.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import java.io.IOException;


public class JsonExtractionTests {

    
    @Test
    public void test_item_extraction() throws Exception {
        Path jsonPath = Paths.get("src/test/java/edu/ucsb/mapache/google/sample.json");
        String body = Files.readString(jsonPath);
        SearchResult searchResult = SearchResult.fromJSON(body);
        assertEquals(searchResult.getItems().get(1).getTitle(), "Springboot -The AJP Connector is configured with secretRequired ...");
    }
}