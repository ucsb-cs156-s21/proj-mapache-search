package edu.ucsb.mapache.google;
import java.util.Objects;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchResult{

    private static final Logger logger = LoggerFactory.getLogger(SearchResult.class);

    private String kind;
    private Url url;
    private Queries query;
    // skipped over context, search, spelling
    private List<Item> items;


    public SearchResult() {
    }

    public SearchResult(String kind, Url url, Queries query, List<Item> items) {
        this.kind = kind;
        this.url = url;
        this.query = query;
        this.items = items;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Url getUrl() {
        return this.url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Queries getQuery() {
        return this.query;
    }

    public void setQuery(Queries query) {
        this.query = query;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public SearchResult kind(String kind) {
        setKind(kind);
        return this;
    }

    public SearchResult url(Url url) {
        setUrl(url);
        return this;
    }

    public SearchResult query(Queries query) {
        setQuery(query);
        return this;
    }

    public SearchResult items(List<Item> items) {
        setItems(items);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SearchResult)) {
            return false;
        }
        SearchResult searchResult = (SearchResult) o;
        return Objects.equals(kind, searchResult.kind) && Objects.equals(url, searchResult.url) && Objects.equals(query, searchResult.query) && Objects.equals(items, searchResult.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, url, query, items);
    }

    @Override
    public String toString() {
        return "{" +
            " kind='" + getKind() + "'" +
            ", url='" + getUrl() + "'" +
            ", query='" + getQuery() + "'" +
            ", items='" + getItems() + "'" +
            "}";
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
}