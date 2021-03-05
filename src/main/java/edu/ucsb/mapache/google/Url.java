package edu.ucsb.mapache.google;
import java.util.Objects;

public class Url{
    private String type;
    private String template;


    public Url() {
    }

    public Url(String type, String template) {
        this.type = type;
        this.template = template;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Url)) {
            return false;
        }
        Url url = (Url) o;
        return Objects.equals(type, url.type) && Objects.equals(template, url.template);
    }

    // not relevant to current work
    // @Override
    // public int hashCode() {
    //     return Objects.hash(type, template);
    // }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", template='" + getTemplate() + "'" +
            "}";
    }

}