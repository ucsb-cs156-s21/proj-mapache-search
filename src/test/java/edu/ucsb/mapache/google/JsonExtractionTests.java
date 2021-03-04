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
    public void test_null_extraction() throws Exception {
        String body = "";
        SearchResult searchResult = SearchResult.fromJSON(body);
        assertEquals(searchResult, null);
    }


    @Test
    public void test_item_extraction() throws Exception {
        Path jsonPath = Paths.get("src/test/java/edu/ucsb/mapache/google/sample.json");
        String body = Files.readString(jsonPath);
        SearchResult searchResult = SearchResult.fromJSON(body);
        assertEquals("Springboot -The AJP Connector is configured with secretRequired ...", searchResult.getItems().get(1).getTitle());
    }

    @Test
    public void test_url_extraction() throws Exception {
        Path jsonPath = Paths.get("src/test/java/edu/ucsb/mapache/google/sample.json");
        String body = Files.readString(jsonPath);
        SearchResult searchResult = SearchResult.fromJSON(body);
        assertEquals("https://www.googleapis.com/customsearch/v1?q={searchTerms}&num={count?}&start={startIndex?}&lr={language?}&safe={safe?}&cx={cx?}&sort={sort?}&filter={filter?}&gl={gl?}&cr={cr?}&googlehost={googleHost?}&c2coff={disableCnTwTranslation?}&hq={hq?}&hl={hl?}&siteSearch={siteSearch?}&siteSearchFilter={siteSearchFilter?}&exactTerms={exactTerms?}&excludeTerms={excludeTerms?}&linkSite={linkSite?}&orTerms={orTerms?}&relatedSite={relatedSite?}&dateRestrict={dateRestrict?}&lowRange={lowRange?}&highRange={highRange?}&searchType={searchType}&fileType={fileType?}&rights={rights?}&imgSize={imgSize?}&imgType={imgType?}&imgColorType={imgColorType?}&imgDominantColor={imgDominantColor?}&alt=json",
        searchResult.getUrl().getTemplate());
    }

    @Test
    public void test_queries_extraction() throws Exception {
        Path jsonPath = Paths.get("src/test/java/edu/ucsb/mapache/google/sample.json");
        String body = Files.readString(jsonPath);
        SearchResult searchResult = SearchResult.fromJSON(body);;
        assertEquals("001539284272632380888:kn5n6ubsr7x", searchResult.getQueries().getRequest().get(0).getCx());
    }
}