package edu.ucsb.mapache.documents;

import java.util.Objects;

public class SlackUserProfile {
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SlackUserProfile that = (SlackUserProfile) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "SlackUserProfile{" + "email='" + email + '\'' + '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public SlackUserProfile() {
    }

    public SlackUserProfile(String email) {
        this.email = email;
    }
}
