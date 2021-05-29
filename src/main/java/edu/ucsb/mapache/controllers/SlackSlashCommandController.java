package edu.ucsb.mapache.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ucsb.mapache.models.SlackSlashCommandParams;
import edu.ucsb.mapache.repositories.ChannelRepository;
import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.google.Item;
import edu.ucsb.mapache.google.SearchResult;
import edu.ucsb.mapache.google.Queries;
import edu.ucsb.mapache.google.SearchResult;
import edu.ucsb.mapache.google.RequestItem;
import edu.ucsb.mapache.services.GoogleSearchService;
import edu.ucsb.mapache.services.GoogleSearchServiceHelper;
import edu.ucsb.mapache.services.NowService;
import edu.ucsb.mapache.services.TeamEmailListService;
import edu.ucsb.mapache.services.TeamListService;

import java.io.IOException;
import java.util.ArrayList;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.HashMap;

import edu.ucsb.mapache.models.SearchParameters;

/**
 * Sample Slash Command Handler.
 *
 * @author ramswaroop
 * @version 1.0.0, 20/06/2016
 */
@RestController
public class SlackSlashCommandController {

    private static final Logger logger = LoggerFactory.getLogger(SlackSlashCommandController.class);

    @Autowired
    ChannelRepository channelRepository;


    @Autowired
    GoogleSearchServiceHelper googleSearchServiceHelper;

    @Autowired
    TeamEmailListService teamEmailListService;

    @Autowired
    TeamListService teamListService;

    @Value("${app.slack.slashCommandToken}")
    private String slackToken;

    @Value("${app.google.search.apiToken}")
    private String apiToken;

    @Autowired
    private NowService nowService;

    public String getSlackToken() {
        return slackToken;
    }

    /**
     * Slash Command handler. When a user types for example "/app help" then slack
     * sends a POST request to this endpoint. So, this endpoint should match the url
     * you set while creating the Slack Slash Command.
     *
     * @param token
     * @param teamId
     * @param teamDomain
     * @param channelId
     * @param channelName
     * @param userId
     * @param userName
     * @param command
     * @param text
     * @param responseUrl
     * @return
     */
    @RequestMapping(value = "/api/public/slash-command", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RichMessage onReceiveSlashCommand(@RequestParam("token") String token,
            @RequestParam("team_id") String teamId, @RequestParam("team_domain") String teamDomain,
            @RequestParam("channel_id") String channelId, @RequestParam("channel_name") String channelName,
            @RequestParam("user_id") String userId, @RequestParam("user_name") String userName,
            @RequestParam("command") String command, @RequestParam("text") String text,
            @RequestParam("response_url") String responseUrl) {
        // validate token
        logger.info("slash command processing...");
        if (!token.equals(slackToken)) {
            return new RichMessage("Sorry: the slack bot received an invalid token: " + token);
        }

        SlackSlashCommandParams params = new SlackSlashCommandParams();

        params.setToken(token);
        params.setTeamId(teamId);
        params.setTeamDomain(teamDomain);
        params.setChannelId(channelId);
        params.setChannelName(channelName);
        params.setUserId(userId);
        params.setCommand(command);
        params.setText(text);
        params.setResponseUrl(responseUrl);

        String[] textParts = params.getTextParts();

        if (textParts.length <= 0 || textParts[0].equals("")) {
            return emptyCommand(params);
        }

        String firstArg = textParts[0];

        if (firstArg.equals("status")) {
            return statusCommand(params);
        }

        if (firstArg.equals("time")) {
            return timeCommand(params);
        }

        if (firstArg.equals("debug")) {
            return debugCommand(params);
        }

        if (firstArg.equals("search") && textParts[1].equals("google")) {
            return googleSearch(params);
        }

        if (firstArg.equals("teamlist")) {
            return getTeamValues(params);
        }

        if (firstArg.equals("members")) {
            return getTeamMembers(params);
        }

        return unknownCommand(params);

    }

    public RichMessage unknownCommand(SlackSlashCommandParams params) {
        RichMessage richMessage = new RichMessage(String.format("Sorry but %s doesn't know the command %s",
                params.getCommand(), params.getTextParts()[0]));
        richMessage.setResponseType("ephemeral");
        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
    }

    public RichMessage emptyCommand(SlackSlashCommandParams params) {
        RichMessage richMessage = new RichMessage(
                String.format("The %s bot knows these commands", params.getCommand()));

        richMessage.setResponseType("ephemeral");

        int numAttachments = 3;
        Attachment[] attachments = new Attachment[numAttachments];
        for (int i = 0; i < numAttachments; i++)
            attachments[i] = new Attachment();
        attachments[0].setText(String.format("%s status", params.getCommand()));
        attachments[1].setText(String.format("%s time", params.getCommand()));
        attachments[2].setText(String.format("%s debug", params.getCommand()));
        richMessage.setAttachments(attachments);

        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
    }

    public RichMessage statusCommand(SlackSlashCommandParams params) {
        RichMessage richMessage = new RichMessage(

                String.format("From: %s Status is ok!", params.getCommand()));
        richMessage.setResponseType("ephemeral");

        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
    }

    
    
    public RichMessage googleSearch(SlackSlashCommandParams params) { // google search
        
        
        SearchParameters sp = new SearchParameters();

        String attachments = "";
        String[] textParts = params.getTextParts();
        for (int i = 2; i < textParts.length; i++) {
            if (i != 2) {attachments += "+";}
            attachments += textParts[i];
        }

        sp.setQuery(attachments);
        sp.setPage(1);
        String body = googleSearchServiceHelper.getJSON(sp,apiToken);

        SearchResult searchResult = SearchResult.fromJSON(body);
        logger.info("searchResult = {}", searchResult);
        Queries searchResultQuery = searchResult.getQueries();
        RequestItem ritem = searchResultQuery.getRequest().get(0);
        RichMessage richMessage = new RichMessage("*Showing Results for:* " + ritem.getSearchTerms() +"\n*Number of Results:* " + ritem.getTotalResults() + "\n *Showing:* " + ritem.getCount());
        int numAttachments = searchResult.getItems().size();
        Attachment[] attachments2;
        if(numAttachments == 0)
        {
            attachments2 = new Attachment[1];
            attachments2[0] = new Attachment();
            attachments2[0].setText("No results found!");
        }
        else
        {
            attachments2 = new Attachment[numAttachments];
            for (int i = 0; i < numAttachments; i++){
                attachments2[i] = new Attachment();
                Item item = searchResult.getItems().get(i);
                attachments2[i].setText("<"   + item.getLink() + "|" + item.getTitle() + ">\n" + item.getSnippet()+ "\n\n"); 
            }
        }
        richMessage.setAttachments(attachments2);


        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
    }


    public RichMessage timeCommand(SlackSlashCommandParams params) {
        String message = String.format("From: %s... the time on the server is %s", params.getCommand(), timeNow());
        RichMessage richMessage = new RichMessage(message);
        richMessage.setResponseType("ephemeral");
        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
    }

    public RichMessage debugCommand(SlackSlashCommandParams params) {
        /** build response */
        RichMessage richMessage = new RichMessage(String.format("Here is what %s knows:", params.getCommand()));
        richMessage.setResponseType("in_channel"); // other option is "ephemeral"

        // set attachments
        int numAttachments = 8;
        Attachment[] attachments = new Attachment[numAttachments];
        for (int i = 0; i < numAttachments; i++)
            attachments[i] = new Attachment();
        attachments[0].setText(String.format("team_id=%s", params.getTeamId()));
        attachments[1].setText(String.format("team_domain=%s", params.getTeamDomain()));
        attachments[2].setText(String.format("channel_id=%s", params.getChannelId()));
        attachments[3].setText(String.format("channel_name=%s", params.getChannelName()));
        attachments[4].setText(String.format("user_id=%s", params.getUserId()));
        attachments[5].setText(String.format("user_name=%s", params.getUserName()));
        attachments[6].setText(String.format("command=%s", params.getCommand()));
        attachments[7].setText(String.format("text=%s", params.getText()));

        richMessage.setAttachments(attachments);

        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
    }

    public RichMessage getTeamValues(SlackSlashCommandParams params) {
        String[] textParts = params.getTextParts();
        if(textParts.length < 2) {
            String teamlistText = teamListService.getListOfTeams();
            RichMessage richMessage = new RichMessage(teamlistText);
            richMessage.setResponseType("in_channel"); // other option is "ephemeral"
            return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
        }

        String teamName = textParts[1];
        String emailText = teamEmailListService.getEmailsStringFromTeamname(teamName);
        RichMessage richMessage = new RichMessage(emailText);
        richMessage.setResponseType("in_channel"); // other option is "ephemeral"
        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack

    }

    public Rich Message getTeamMembers(SlackSlashCommandParams params) {
        String[] textParts = params.getTextParts();
        if(textPars.length < 2) {

        String teamName = textParts[1];
        String teamMembersList = MembersListService.getListOfMembers();
        RichMessage richMessage = new RichMessage(teamMebmersList);
        richMessage.setResponseType("in_channel"); // other option is "ephemeral"
        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
    }

    private String timeNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        return formatter.format(nowService.now());
    }

}
