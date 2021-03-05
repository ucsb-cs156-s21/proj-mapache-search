package edu.ucsb.mapache.google;
import java.util.List;
import java.util.Objects;

public class Queries{
    private List<RequestItem> request;
    private List<RequestItem> nextPage;


    public Queries() {
    }

    public Queries(List<RequestItem> request, List<RequestItem> nextPage) {
        this.request = request;
        this.nextPage = nextPage;
    }

    public List<RequestItem> getRequest() {
        return this.request;
    }

    public void setRequest(List<RequestItem> request) {
        this.request = request;
    }

    public List<RequestItem> getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(List<RequestItem> nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Queries)) {
            return false;
        }
        Queries queries = (Queries) o;
        return Objects.equals(request, queries.request) && Objects.equals(nextPage, queries.nextPage);
    }

    // Not relavent for current work
    // @Override
    // public int hashCode() {
    //     return Objects.hash(request, nextPage);
    // }

    @Override
    public String toString() {
        return "{" +
            " request='" + getRequest() + "'" +
            ", nextPage='" + getNextPage() + "'" +
            "}";
    }

}