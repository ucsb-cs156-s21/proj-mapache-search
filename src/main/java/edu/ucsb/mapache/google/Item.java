package edu.ucsb.mapache.google;
import java.util.Objects;

public class Item{
    private String kind;
    private String title;
    private String htmlTitle;
    private String link;
    private String displayLink;
    private String snippet;
    private String htmlSnippet;
    private String cacheId;
    private String formattedUrl;
    private String htmlFormattedUrl;



    public Item() {
    }

    public Item(String kind, String title, String htmlTitle, String link, String displayLink, String snippet, String htmlSnippet, String cacheId, String formattedUrl, String htmlFormattedUrl) {
        this.kind = kind;
        this.title = title;
        this.htmlTitle = htmlTitle;
        this.link = link;
        this.displayLink = displayLink;
        this.snippet = snippet;
        this.htmlSnippet = htmlSnippet;
        this.cacheId = cacheId;
        this.formattedUrl = formattedUrl;
        this.htmlFormattedUrl = htmlFormattedUrl;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlTitle() {
        return this.htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDisplayLink() {
        return this.displayLink;
    }

    public void setDisplayLink(String displayLink) {
        this.displayLink = displayLink;
    }

    public String getSnippet() {
        return this.snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getHtmlSnippet() {
        return this.htmlSnippet;
    }

    public void setHtmlSnippet(String htmlSnippet) {
        this.htmlSnippet = htmlSnippet;
    }

    public String getCacheId() {
        return this.cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public String getFormattedUrl() {
        return this.formattedUrl;
    }

    public void setFormattedUrl(String formattedUrl) {
        this.formattedUrl = formattedUrl;
    }

    public String getHtmlFormattedUrl() {
        return this.htmlFormattedUrl;
    }

    public void setHtmlFormattedUrl(String htmlFormattedUrl) {
        this.htmlFormattedUrl = htmlFormattedUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(kind, item.kind) && Objects.equals(title, item.title) && Objects.equals(htmlTitle, item.htmlTitle) && Objects.equals(link, item.link) && Objects.equals(displayLink, item.displayLink) && Objects.equals(snippet, item.snippet) && Objects.equals(htmlSnippet, item.htmlSnippet) && Objects.equals(cacheId, item.cacheId) && Objects.equals(formattedUrl, item.formattedUrl) && Objects.equals(htmlFormattedUrl, item.htmlFormattedUrl);
    }

    @Override
    public String toString() {
        return "{" +
            " kind='" + getKind() + "'" +
            ", title='" + getTitle() + "'" +
            ", htmlTitle='" + getHtmlTitle() + "'" +
            ", link='" + getLink() + "'" +
            ", displayLink='" + getDisplayLink() + "'" +
            ", snippet='" + getSnippet() + "'" +
            ", htmlSnippet='" + getHtmlSnippet() + "'" +
            ", cacheId='" + getCacheId() + "'" +
            ", formattedUrl='" + getFormattedUrl() + "'" +
            ", htmlFormattedUrl='" + getHtmlFormattedUrl() + "'" +
            "}";
    }

}