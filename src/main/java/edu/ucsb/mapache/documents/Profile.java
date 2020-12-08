package edu.ucsb.mapache.documents;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Profile {
    private String real_name;

    public Profile() { }

    public Profile(String real_name) {
        this.real_name = real_name;
    }

    public void setReal_Name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_Name() {
        return real_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Profile that = (Profile) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(real_name, that.getReal_Name());
        return builder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(real_name);
    }

    @Override
    public String toString() {
        return "Profile{" + "real_name'" + getReal_Name() + '\'' + '}';
    }
}
