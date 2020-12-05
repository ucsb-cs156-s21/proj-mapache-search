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

    //Constructors
    public Message() {
    }

    public Message(String id, String type, String subtype, String ts, String user, String text, String channel) {
        this.id = id;
        this.type = type;
        this.subtype = subtype;
        this.ts = ts;
        this.user = user;
        this.text = text;
        this.channel = channel;
    }

    //Setters
    public void setId(String id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setSubtype(String subtype) { this.subtype = subtype; }
    public void setTs(String ts) { this.ts = ts; }
    public void setUser(String user) { this.user = user; }
    public void setText(String text) { this.text = text; }
    public void setChannel(String channel) { this.channel = channel; }
    //Getters
    public String getId() { return this.id; }
    public String getType() { return this.type; }
    public String getSubtype() { return this.subtype; }
    public String getTs() { return this.ts; }
    public String getUser() { return this.user; }
    public String getText() { return this.text; }
    public String getChannel() { return this.channel; }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Message))
            return false;
        Message m = (Message) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(text, m.getText());
        return builder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", subtype='" + getSubtype() + "'" +
            ", ts='" + getTs() + "'" +
            ", user='" + getUser() + "'" +
            ", text='" + getText() + "'" +
            ", channel='" + getChannel() + "'" +
            "}";
    }
}

/*

Example of the JSON for a Message document
{
    "_id": "5fb068cd0a8dccb8dbab0aaf",
    "type": "message",
    "subtype": "channel_join",
    "ts": "1602624910.000200",
    "user": "U0185QQSJBY",
    "text": "<@U0185QQSJBY> has joined the channel",
    "channel": "section-6pm"
}

*/