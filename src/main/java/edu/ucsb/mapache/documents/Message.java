package edu.ucsb.mapache.documents;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


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
    private List<String> attachments;
    private List<String> blocks;
    private String purpose;
    private List<String> reactions;
    private String thread_ts;
    private Integer reply_count;
    private Integer reply_users_count;
    private String latest_reply;

    public Message(String type, String subtype, String ts, String user, String channel, String channel_msg_id, String team, String user_team, String source_team, SlackUserProfile user_profile, List<String> attachments, List<String> blocks, String purpose, List<String> reactions, String thread_ts, Integer reply_count, Integer reply_users_count, String latest_reply) {
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
        this.attachments = attachments;
        this.blocks = blocks;
        this.purpose = purpose;
        this.reactions = reactions;
        this.thread_ts = thread_ts;
        this.reply_count = reply_count;
        this.reply_users_count = reply_users_count;
        this.latest_reply = latest_reply;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getTs() {
        return ts;
    }

    public String getUser() {
        return user;
    }

    public String getChannel() {
        return channel;
    }

    public String getChannel_msg_id() {
        return channel_msg_id;
    }

    public String getTeam() {
        return team;
    }

    public String getUser_team() {
        return user_team;
    }

    public String getSource_team() {
        return source_team;
    }

    public SlackUserProfile getUser_profile() {
        return user_profile;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public List<String> getBlocks() {
        return blocks;
    }

    public String getPurpose() {
        return purpose;
    }

    public List<String> getReactions() {
        return reactions;
    }

    public String getThread_ts() {
        return thread_ts;
    }

    public Integer getReply_count() {
        return reply_count;
    }

    public Integer getReply_users_count() {
        return reply_users_count;
    }

    public String getLatest_reply() {
        return latest_reply;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setChannel_msg_id(String channel_msg_id) {
        this.channel_msg_id = channel_msg_id;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setUser_team(String user_team) {
        this.user_team = user_team;
    }

    public void setSource_team(String source_team) {
        this.source_team = source_team;
    }

    public void setUser_profile(SlackUserProfile user_profile) {
        this.user_profile = user_profile;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public void setBlocks(List<String> blocks) {
        this.blocks = blocks;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setReactions(List<String> reactions) {
        this.reactions = reactions;
    }

    public void setThread_ts(String thread_ts) {
        this.thread_ts = thread_ts;
    }

    public void setReply_count(Integer reply_count) {
        this.reply_count = reply_count;
    }

    public void setReply_users_count(Integer reply_users_count) {
        this.reply_users_count = reply_users_count;
    }

    public void setLatest_reply(String latest_reply) {
        this.latest_reply = latest_reply;
    }
}
