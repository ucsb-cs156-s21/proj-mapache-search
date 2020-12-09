package edu.ucsb.mapache.documents;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class SlackUserProfile {
    private String email;
    private String real_name;
    private String display_name;
    private String name;

    public SlackUserProfile() {
    }

    public SlackUserProfile(String email) {
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public String getReal_name() {
        return real_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SlackUserProfile(String email, String real_name, String display_name, String name) {
        this.email = email;
        this.real_name = real_name;
        this.display_name = display_name;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlackUserProfile that = (SlackUserProfile) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(email, that.getEmail());
        builder.append(real_name, that.getReal_name());
        builder.append(display_name, that.getDisplay_name());
        builder.append(name, that.getName());
        return builder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, real_name, display_name, name);
    }

    @Override
    public String toString() {
        return "SlackUserProfile{" +
                "email='" + email + '\'' +
                ", real_name='" + real_name + '\'' +
                ", display_name='" + display_name + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
