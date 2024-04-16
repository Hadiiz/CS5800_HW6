import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Message {
    private User sender;
    private List<User> receivers;
    private String textMessage;
    private Date timestamp;

    public Message(User sender, List<User> receivers, String textMessage) {
        this.sender = sender;
        this.receivers = receivers;
        this.textMessage = textMessage;
        this.timestamp = new Date();
    }

    public MessageMememto saveToMememto(){
        return new MessageMememto(this);
    }

    public void restoreFromMememto(MessageMememto messageMememto){
        Message previousMessage = messageMememto.getPreviousMessage();
        this.sender = previousMessage.getSender();
        this.receivers = previousMessage.getReceivers();
        this.textMessage = previousMessage.getTextMessage();
        this.timestamp = previousMessage.getTimestamp();
    }

    public String getTextMessage(){
        return textMessage;
    }

    public List<User> getReceivers(){
        return receivers;
    }


    public User getSender(){
        return sender;
    }



    public String toString() {
        return "Message Timestamp: " + timestamp.toString() + " | Content: '" + textMessage + "'";
    }

    public Date getTimestamp(){
        return timestamp;
    }
}