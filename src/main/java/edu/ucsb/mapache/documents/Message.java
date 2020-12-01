package edu.ucsb.mapache.documents;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;


@Document(collection = "messages")
public class Message {
    private String type;
    private String subtype;
    private String ts;
    private String user;
    private String channel;
    private String channel_msg_id;
    private String team;
    private String user_team;
    private String source_team;
    private SlackUserProfile user_profile;
    private String purpose;
    private List<String> reactions;
    private String thread_ts;
    private Integer reply_count;
    private Integer reply_users_count;
    private String latest_reply;
    private List<String> reply_users;
    private List<String> replies;
    private Boolean subscribed;
    private String last_read;
    private String parent_user_id;

    public Message(String type, String subtype, String ts, String user, String channel, String channel_msg_id, String team, String user_team, String source_team, SlackUserProfile user_profile, String purpose, List<String> reactions, String thread_ts, Integer reply_count, Integer reply_users_count, String latest_reply, List<String> reply_users, List<String> replies, Boolean subscribed, String last_read, String parent_user_id) {
        this.type = type;
        this.subtype = subtype;
        this.ts = ts;
        this.user = user;
        this.channel = channel;
        this.channel_msg_id = channel_msg_id;
        this.team = team;
        this.user_team = user_team;
        this.source_team = source_team;
        this.user_profile = user_profile;
        this.purpose = purpose;
        this.reactions = reactions;
        this.thread_ts = thread_ts;
        this.reply_count = reply_count;
        this.reply_users_count = reply_users_count;
        this.latest_reply = latest_reply;
        this.reply_users = reply_users;
        this.replies = replies;
        this.subscribed = subscribed;
        this.last_read = last_read;
        this.parent_user_id = parent_user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel_msg_id() {
        return channel_msg_id;
    }

    public void setChannel_msg_id(String channel_msg_id) {
        this.channel_msg_id = channel_msg_id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getUser_team() {
        return user_team;
    }

    public void setUser_team(String user_team) {
        this.user_team = user_team;
    }

    public String getSource_team() {
        return source_team;
    }

    public void setSource_team(String source_team) {
        this.source_team = source_team;
    }

    public SlackUserProfile getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(SlackUserProfile user_profile) {
        this.user_profile = user_profile;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<String> getReactions() {
        return reactions;
    }

    public void setReactions(List<String> reactions) {
        this.reactions = reactions;
    }

    public String getThread_ts() {
        return thread_ts;
    }

    public void setThread_ts(String thread_ts) {
        this.thread_ts = thread_ts;
    }

    public Integer getReply_count() {
        return reply_count;
    }

    public void setReply_count(Integer reply_count) {
        this.reply_count = reply_count;
    }

    public Integer getReply_users_count() {
        return reply_users_count;
    }

    public void setReply_users_count(Integer reply_users_count) {
        this.reply_users_count = reply_users_count;
    }

    public String getLatest_reply() {
        return latest_reply;
    }

    public void setLatest_reply(String latest_reply) {
        this.latest_reply = latest_reply;
    }

    public List<String> getReply_users() {
        return reply_users;
    }

    public void setReply_users(List<String> reply_users) {
        this.reply_users = reply_users;
    }

    public List<String> getReplies() {
        return replies;
    }

    public void setReplies(List<String> replies) {
        this.replies = replies;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getLast_read() {
        return last_read;
    }

    public void setLast_read(String last_read) {
        this.last_read = last_read;
    }

    public String getParent_user_id() {
        return parent_user_id;
    }

    public void setParent_user_id(String parent_user_id) {
        this.parent_user_id = parent_user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(type, message.type) &&
                Objects.equals(subtype, message.subtype) &&
                Objects.equals(ts, message.ts) &&
                Objects.equals(user, message.user) &&
                Objects.equals(channel, message.channel) &&
                Objects.equals(channel_msg_id, message.channel_msg_id) &&
                Objects.equals(team, message.team) &&
                Objects.equals(user_team, message.user_team) &&
                Objects.equals(source_team, message.source_team) &&
                Objects.equals(user_profile, message.user_profile) &&
                Objects.equals(purpose, message.purpose) &&
                Objects.equals(reactions, message.reactions) &&
                Objects.equals(thread_ts, message.thread_ts) &&
                Objects.equals(reply_count, message.reply_count) &&
                Objects.equals(reply_users_count, message.reply_users_count) &&
                Objects.equals(latest_reply, message.latest_reply) &&
                Objects.equals(reply_users, message.reply_users) &&
                Objects.equals(replies, message.replies) &&
                Objects.equals(subscribed, message.subscribed) &&
                Objects.equals(last_read, message.last_read) &&
                Objects.equals(parent_user_id, message.parent_user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, subtype, ts, user, channel, channel_msg_id, team, user_team, source_team, user_profile, purpose, reactions, thread_ts, reply_count, reply_users_count, latest_reply, reply_users, replies, subscribed, last_read, parent_user_id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", subtype='" + subtype + '\'' +
                ", ts='" + ts + '\'' +
                ", user='" + user + '\'' +
                ", channel='" + channel + '\'' +
                ", channel_msg_id='" + channel_msg_id + '\'' +
                ", team='" + team + '\'' +
                ", user_team='" + user_team + '\'' +
                ", source_team='" + source_team + '\'' +
                ", user_profile=" + user_profile +
                ", purpose='" + purpose + '\'' +
                ", reactions=" + reactions +
                ", thread_ts='" + thread_ts + '\'' +
                ", reply_count=" + reply_count +
                ", reply_users_count=" + reply_users_count +
                ", latest_reply='" + latest_reply + '\'' +
                ", reply_users=" + reply_users +
                ", replies=" + replies +
                ", subscribed=" + subscribed +
                ", last_read='" + last_read + '\'' +
                ", parent_user_id='" + parent_user_id + '\'' +
                '}';
    }
}
