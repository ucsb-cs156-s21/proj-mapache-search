package edu.ucsb.mapache.documents;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class SlackUser {
    @Field("id")
    private String id;
    private String name;
    private String real_name;
    private SlackUserProfile profile;

    public SlackUser() { }

    public SlackUser(String id, String name, String real_name, SlackUserProfile profile) {
        this.id = id;
        this.name = name;
        this.real_name = real_name;
        this.profile = profile;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getReal_name() { return real_name; }

    public void setReal_name(String real_name) { this.real_name = real_name; }

    public SlackUserProfile getProfile() { return profile; }

    public void setProfile(SlackUserProfile profile) { this.profile = profile; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SlackUser slackUser = (SlackUser) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(id, slackUser.id).append(name, slackUser.name).append(real_name, slackUser.real_name)
                .append(profile, slackUser.profile);
        return builder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, real_name, profile);
    }

    @Override
    public String toString() {
        return "SlackUser{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", real_name='" + real_name + '\''
                + ", profile=" + profile + '}';
    }
}
