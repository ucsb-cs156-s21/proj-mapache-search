package edu.ucsb.mapache.google;
import java.util.Objects;

public class RequestItem{
    private String title;
    private String totalResults;
    private String searchTerms;
    private int count;
    private int startIndex;
    private String inputEncoding;
    private String outputEncoding;
    private String safe;
    private String cx;


    public RequestItem() {
    }

    public RequestItem(String title, String totalResults, String searchTerms, int count, int startIndex, String inputEncoding, String outputEncoding, String safe, String cx) {
        this.title = title;
        this.totalResults = totalResults;
        this.searchTerms = searchTerms;
        this.count = count;
        this.startIndex = startIndex;
        this.inputEncoding = inputEncoding;
        this.outputEncoding = outputEncoding;
        this.safe = safe;
        this.cx = cx;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalResults() {
        return this.totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getSearchTerms() {
        return this.searchTerms;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getInputEncoding() {
        return this.inputEncoding;
    }

    public void setInputEncoding(String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }

    public String getOutputEncoding() {
        return this.outputEncoding;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    public String getSafe() {
        return this.safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public String getCx() {
        return this.cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RequestItem)) {
            return false;
        }
        RequestItem requestItem = (RequestItem) o;
        return Objects.equals(title, requestItem.title) && Objects.equals(totalResults, requestItem.totalResults) && Objects.equals(searchTerms, requestItem.searchTerms) && count == requestItem.count && startIndex == requestItem.startIndex && Objects.equals(inputEncoding, requestItem.inputEncoding) && Objects.equals(outputEncoding, requestItem.outputEncoding) && Objects.equals(safe, requestItem.safe) && Objects.equals(cx, requestItem.cx);
    }

    @Override
    public String toString() {
        return "{" +
            " title='" + getTitle() + "'" +
            ", totalResults='" + getTotalResults() + "'" +
            ", searchTerms='" + getSearchTerms() + "'" +
            ", count='" + getCount() + "'" +
            ", startIndex='" + getStartIndex() + "'" +
            ", inputEncoding='" + getInputEncoding() + "'" +
            ", outputEncoding='" + getOutputEncoding() + "'" +
            ", safe='" + getSafe() + "'" +
            ", cx='" + getCx() + "'" +
            "}";
    }

}