package edu.ucsb.mapache.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
        Item item = searchResult.getItems().get(1);
        Item expected = new Item("customsearch#result",
        "Springboot -The AJP Connector is configured with secretRequired ...",
        "\u003cb\u003eSpringboot\u003c/b\u003e -The AJP Connector is configured with secretRequired ...",
        "https://stackoverflow.com/questions/60501470/springboot-the-ajp-connector-is-configured-with-secretrequired-true-but-the-s",
        "stackoverflow.com",
        "Here is one solution, though probably not the best one, but my focus was not this, \njust to pass through the error, I was enabling AJP on Spring ...",
        "Here is one solution, though probably not the best one, but my focus was not this, \u003cbr\u003e\njust to pass through the error, I was enabling AJP on \u003cb\u003eSpring\u003c/b\u003e&nbsp;...",
        "DhhFMiU1HdYJ",
        "https://stackoverflow.com/.../springboot-the-ajp-connector-is-configured-with -secretrequired-true-but-the-s",
        "https://stackoverflow.com/.../\u003cb\u003espringboot\u003c/b\u003e-the-ajp-connector-is-configured-with -secretrequired-true-but-the-s");
        assertEquals(expected.toString(), item.toString());
    }

    @Test
    public void test_url_extraction() throws Exception {
        Path jsonPath = Paths.get("src/test/java/edu/ucsb/mapache/google/sample.json");
        String body = Files.readString(jsonPath);
        SearchResult searchResult = SearchResult.fromJSON(body);
        Url expected = new Url("application/json",
        "https://www.googleapis.com/customsearch/v1?q={searchTerms}&num={count?}&start={startIndex?}&lr={language?}&safe={safe?}&cx={cx?}&sort={sort?}&filter={filter?}&gl={gl?}&cr={cr?}&googlehost={googleHost?}&c2coff={disableCnTwTranslation?}&hq={hq?}&hl={hl?}&siteSearch={siteSearch?}&siteSearchFilter={siteSearchFilter?}&exactTerms={exactTerms?}&excludeTerms={excludeTerms?}&linkSite={linkSite?}&orTerms={orTerms?}&relatedSite={relatedSite?}&dateRestrict={dateRestrict?}&lowRange={lowRange?}&highRange={highRange?}&searchType={searchType}&fileType={fileType?}&rights={rights?}&imgSize={imgSize?}&imgType={imgType?}&imgColorType={imgColorType?}&imgDominantColor={imgDominantColor?}&alt=json");
        assertEquals(expected.toString(), searchResult.getUrl().toString());
    }

    @Test
    public void test_queries_extraction() throws Exception {
        Path jsonPath = Paths.get("src/test/java/edu/ucsb/mapache/google/sample.json");
        String body = Files.readString(jsonPath);
        SearchResult searchResult = SearchResult.fromJSON(body);
        List<RequestItem> r = new ArrayList<RequestItem>();
        r.add(new RequestItem(
        "Google Custom Search - springboot",
        "408000",
        "springboot",
        10,
        1,
        "utf8",
        "utf8",
        "off",
        "001539284272632380888:kn5n6ubsr7x"
        ));
        List<RequestItem> n = new ArrayList<RequestItem>();
        n.add(new RequestItem(
            "Google Custom Search - springboot",
            "408000",
            "springboot",
            10,
            11,
            "utf8",
            "utf8",
            "off",
            "001539284272632380888:kn5n6ubsr7x"
        ));
        Queries expected = new Queries(r, n);
        assertEquals(expected.toString(), searchResult.getQueries().toString()); 
    }
}