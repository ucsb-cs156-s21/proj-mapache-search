// //Standard Slack User along with their messages
// package edu.ucsb.mapache.documents;

// import java.util.List;
// import java.util.Objects;
// import java.util.stream.Collectors;

// import org.apache.commons.lang3.builder.EqualsBuilder;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.data.mongodb.core.mapping.Document;

// @Document(collection = "users")
// public class SlackUserWithMessages extends SlackUser {
//     //List of all messages sent by a specific user
//     private List<Message> messages;

//     public SlackUserWithMessages() {
//     }

//     public SlackUserWithMessages(List<Message> messages) {
//         this.messages.addAll(messages);
//     } 

//     public List<Message> getMessages() { return this.messages; }
//     public void setMessages(List<Message> messages) { this.messages = messages; }

//     //Adding an individual message
//     public void addMessage(Message message) { this.messages.add(message); }

// }
