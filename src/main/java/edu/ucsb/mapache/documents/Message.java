package edu.ucsb.mapache.documents;

import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Message {
    private String id;
    private String type;
    private String subtype;
    private String ts;
    private String user;
    private String text;
    private String channel;
    private Profile user_profile;

    public Message() {
    }

    public Message(String id, String type, String subtype, String ts, String user, String text, String channel, Profile user_profile) {
        this.id = id;
        this.type = type;
        this.subtype = subtype;
        this.ts = ts;
        this.user = user;
        this.text = text;
        this.channel = channel;
        this.user_profile = user_profile;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return this.subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getTs() {
        return this.ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Profile getUser_Profile(){return user_profile;}

    public void setUser_Profile(Profile user_profile){this.user_profile = user_profile;}
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Message)) {
            return false;
        }
        Message c = (Message) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(text, c.getText());
        return builder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", type='" + getType() + "'" + ", subtype='" + getSubtype() + "'"
                + ", ts='" + getTs() + "'" + ", user='" + getUser() + "'" + ", text='" + getText() + "'" + ", channel='"
                + getChannel() +"'" +", user_profile='" + getUser_Profile()+"'"+ "}";
    }
}