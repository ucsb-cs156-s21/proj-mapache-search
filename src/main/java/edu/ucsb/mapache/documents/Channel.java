package edu.ucsb.mapache.documents;

import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "channels")
public class Channel {
    private String id;
    private String name;
    private String creator;
    private Boolean is_archived;
    private Boolean is_general;
    private List<String> members;
    private ChannelTopic topic;
    private ChannelPurpose purpose;

    public Channel() {
    }

    public Channel(String id, String name, String creator, Boolean is_archived, Boolean is_general, List<String> members, ChannelTopic topic, ChannelPurpose purpose) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.is_archived = is_archived;
        this.is_general = is_general;
        this.members = members;
        this.topic = topic;
        this.purpose = purpose;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Boolean isIs_archived() {
        return this.is_archived;
    }

    public Boolean getIs_archived() {
        return this.is_archived;
    }

    public void setIs_archived(Boolean is_archived) {
        this.is_archived = is_archived;
    }

    public Boolean isIs_general() {
        return this.is_general;
    }

    public Boolean getIs_general() {
        return this.is_general;
    }

    public void setIs_general(Boolean is_general) {
        this.is_general = is_general;
    }

    public List<String> getMembers() {
        return this.members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public ChannelTopic getTopic() {
        return this.topic;
    }

    public void setTopic(ChannelTopic topic) {
        this.topic = topic;
    }

    public ChannelPurpose getPurpose() {
        return this.purpose;
    }

    public void setPurpose(ChannelPurpose purpose) {
        this.purpose = purpose;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Channel)) {
            return false;
        }
        Channel c = (Channel) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(name, c.getName());
        return builder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", creator='" + getCreator() + "'" +
            ", is_archived='" + isIs_archived() + "'" +
            ", is_general='" + isIs_general() + "'" +
            ", members='" + getMembers() + "'" +
            ", topic='" + getTopic() + "'" +
            ", purpose='" + getPurpose() + "'" +
            "}";
    }

    
}

/*

Example of the JSON for a Channel document

{
    "_id": {
        "$oid": "5fb068cd0a8dccb8dbab0a82"
    },
    "id": "C016GMB0H5L",
    "name": "section-6pm",
    "created": {
        "$numberInt": "1594143066"
    },
    "creator": "U017218J9B3",
    "is_archived": false,
    "is_general": false,
    "members": [
        "U017218J9B3",
        "U0185QQSJBY",
        "U018CH1NLPL",
        "U018R1AULF3",
        "U018XEKMRTM",
        "U0192BC785N",
        "U019B1Q0YHW"
    ],
    "topic": {
        "value": "",
        "creator": "",
        "last_set": {
            "$numberInt": "0"
        }
    },
    "purpose": {
        "value": "",
        "creator": "",
        "last_set": {
            "$numberInt": "0"
        }
    }
}


*/
