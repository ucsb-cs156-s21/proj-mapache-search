package edu.ucsb.mapache.google;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Data
public class SearchResult {

    private static final Logger logger = LoggerFactory.getLogger(SearchResult.class);

    private String kind;
    private Url url;
    private Queries queries;
    // skipped over context, search, spelling
    private List<Item> items;

    public SearchResult() {
    }

    public SearchResult(String kind, Url url, Queries queries, List<Item> items) {
        this.kind = kind;
        this.url = url;
        this.queries = queries;
        this.items = items;
    }

    /**
     * Create a SearchResult object from json representation
     * 
     * @param json String of json returned by Google Search API
     * @return a new SearchResult object
     */
    public static SearchResult fromJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SearchResult searchResult = objectMapper.readValue(json, SearchResult.class);
            return searchResult;
        } catch (JsonProcessingException jpe) {
            logger.error("JsonProcessingException:" + jpe);
            return null;
        }

    }

    /**
     * Create a SearchResult object from json representation
     * 
     * @param json String of json returned by Google Search API
     * @return a new SearchResult object
     */
    public String toJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // try {
        return objectMapper.writeValueAsString(this);
        // } catch (JsonProcessingException jpe) {
        //     logger.error("JsonProcessingException: ", jpe);
        //     return "";
        // }
    }

    /**
     * An empty search result
     */

    public static SearchResult empty() {
        SearchResult sr = new SearchResult();
        sr.setKind("empty");
        sr.setUrl(new Url("", ""));
        sr.setItems(new ArrayList<Item>());
        sr.setQueries(new Queries());
        return sr;
    }
}