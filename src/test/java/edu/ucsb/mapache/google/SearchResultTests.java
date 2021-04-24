package edu.ucsb.mapache.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import java.io.IOException;

public class SearchResultTests {


    @Test
    public void test_null_fromJSON() throws Exception {
        String body = "";
        SearchResult searchResult = SearchResult.fromJSON(body);
        assertEquals(searchResult, null);
    }


    @Test
    public void test_item_fromJSON() throws Exception {
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
        assertTrue(expected.equals(item));
    }

    @Test
    public void test_url_fromJSON() throws Exception {
        Path jsonPath = Paths.get("src/test/java/edu/ucsb/mapache/google/sample.json");
        String body = Files.readString(jsonPath);
        SearchResult searchResult = SearchResult.fromJSON(body);
        Url url = searchResult.getUrl();
        Url expected = new Url("application/json",
        "https://www.googleapis.com/customsearch/v1?q={searchTerms}&num={count?}&start={startIndex?}&lr={language?}&safe={safe?}&cx={cx?}&sort={sort?}&filter={filter?}&gl={gl?}&cr={cr?}&googlehost={googleHost?}&c2coff={disableCnTwTranslation?}&hq={hq?}&hl={hl?}&siteSearch={siteSearch?}&siteSearchFilter={siteSearchFilter?}&exactTerms={exactTerms?}&excludeTerms={excludeTerms?}&linkSite={linkSite?}&orTerms={orTerms?}&relatedSite={relatedSite?}&dateRestrict={dateRestrict?}&lowRange={lowRange?}&highRange={highRange?}&searchType={searchType}&fileType={fileType?}&rights={rights?}&imgSize={imgSize?}&imgType={imgType?}&imgColorType={imgColorType?}&imgDominantColor={imgDominantColor?}&alt=json");
        assertEquals(expected.toString(), url.toString());
        assertTrue(expected.equals(url));
    }

    @Test
    public void test_queries_fromJSON() throws Exception {
        Path jsonPath = Paths.get("src/test/java/edu/ucsb/mapache/google/sample.json");
        String body = Files.readString(jsonPath);
        SearchResult searchResult = SearchResult.fromJSON(body);
        Queries queries = searchResult.getQueries();
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
        assertEquals(expected.toString(), queries.toString());
        assertTrue(expected.equals(queries));
    }

    @Test
    public void test_equals0() throws Exception {
        Url url = new Url("test", "test");
        List<RequestItem> r= new ArrayList<RequestItem>();
        r.add(new RequestItem("test", "test", "test", 42, 42, "test", "test", "test", "test"));
        Queries queries = new Queries(r, r);
        List<Item> i = new ArrayList<Item>();
        i.add(new  Item("test", "test", "test", "test", "test", "test", "test", "test", "test", "test"));
        SearchResult searchResult0 = new SearchResult("test", url, queries, i);
        SearchResult searchResult1 = new SearchResult("test", url, queries, i);
        assertTrue(searchResult0.equals(searchResult1));
        assertTrue(searchResult0.equals(searchResult0));
    }

    @Test
    public void test_equals1() throws Exception {
        Url url = new Url("test", "test");
        Url url1 = new Url("fail", "fail");
        List<RequestItem> r= new ArrayList<RequestItem>();
        r.add(new RequestItem("test", "test", "test", 42, 42, "test", "test", "test", "test"));
        List<RequestItem> s= new ArrayList<RequestItem>();
        s.add(new RequestItem("fail", "test", "test", 42, 42, "test", "test", "test", "test"));
        Queries queries = new Queries(r, r);
        Queries queries1 = new Queries(s, s);
        List<Item> i = new ArrayList<Item>();
        i.add(new  Item("test", "test", "test", "test", "test", "test", "test", "test", "test", "test"));
        List<Item> j = new ArrayList<Item>();
        j.add(new  Item("fail", "test", "test", "test", "test", "test", "test", "test", "test", "test"));
        SearchResult searchResult0 = new SearchResult("test", url, queries, i);
        SearchResult searchResult1 = new SearchResult("fail", url, queries, i);
        SearchResult searchResult2 = new SearchResult("test", url1, queries, i);
        SearchResult searchResult3 = new SearchResult("test", url, queries1, i);
        SearchResult searchResult4 = new SearchResult("test", url, queries, j);
        String searchResult5 = "fail";
        assertFalse(searchResult0.equals(searchResult1));
        assertFalse(searchResult0.equals(searchResult2));
        assertFalse(searchResult0.equals(searchResult3));
        assertFalse(searchResult0.equals(searchResult4));
        assertFalse(searchResult0.equals(searchResult5));
    }

    @Test
    public void test_empty() throws Exception {        
        SearchResult sr = SearchResult.empty();
        assertEquals("empty",sr.getKind());
        assertEquals(new Url("",""), sr.getUrl());
        assertEquals(new ArrayList<Item>(), sr.getItems());
        assertEquals(new Queries(), sr.getQueries());
    }

    @Test
    public void test_toJSON() throws Exception {        
        SearchResult sr = SearchResult.empty();
        String actual = sr.toJSON();
        String expected = "{\"kind\":\"empty\",\"url\":{\"type\":\"\",\"template\":\"\"},\"queries\":{\"request\":null,\"nextPage\":null},\"items\":[]}";
        assertEquals(expected, actual);
    }

}