package edu.ucsb.mapache.models;

import java.util.Objects;
import edu.ucsb.mapache.documents.SlackUser;

public class SlackUserWithStats {
    private SlackUser slackUser;
    private int messageCount;

    public SlackUserWithStats() {
    }

    public SlackUserWithStats(SlackUser slackUser, int messageCount) {
        this.slackUser = slackUser;
        this.messageCount = messageCount;
    }

    public SlackUser getSlackUser() {
        return this.slackUser;
    }

    public void setSlackUser(SlackUser slackUser) {
        this.slackUser = slackUser;
    }

    public int getMessageCount() {
        return this.messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public SlackUserWithStats slackUser(SlackUser slackUser) {
        this.slackUser = slackUser;
        return this;
    }

    public SlackUserWithStats messageCount(int messageCount) {
        this.messageCount = messageCount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SlackUserWithStats)) {
            return false;
        }
        SlackUserWithStats slackUserWithStats = (SlackUserWithStats) o;
        return Objects.equals(slackUser, slackUserWithStats.slackUser) && messageCount == slackUserWithStats.messageCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slackUser, messageCount);
    }

    @Override
    public String toString() {
        return "{" +
            " slackUser='" + getSlackUser() + "'" +
            ", messageCount='" + getMessageCount() + "'" +
            "}";
    }



}