package edu.ucsb.mapache.documents;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class MessageReactions {
    private int count; 
    private String name;

    public MessageReactions() {
    }

    public MessageReactions(String name, int count) {
        this.name = name;
        this.count = count;
    }
    
    public int getCount() {
        return count;
    }   

    public String getName() {
        return name;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageReactions(int count, String name) {
        this.count = count;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageReactions that = (MessageReactions) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(count, that.getCount());
        builder.append(name, that.getName());
        return builder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, name);
    }

    @Override
    public String toString() {
        return "SlackUserProfile{" +
                "count='" + count + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
