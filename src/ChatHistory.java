import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatHistory implements Iterable<Message>{
    private List<Message> sentMessages;
    private List<Message> receivedMessages;


    public ChatHistory(){
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();
    }

    public void addReceivedMessage(Message message){
        receivedMessages.add(message);
    }

    public void addSentMessage(Message message){
        sentMessages.add(message);
    }

    public Message getLastSentMessages(){
        if (sentMessages.size() > 0){
            return sentMessages.get(sentMessages.size() - 1);
        } else{
            return null;
        }
    }

    public void removeLastSentMessage(Message message){
        sentMessages.remove(message);
    }

    public List<Message> getSentMessages(){
        return sentMessages;
    }

    public void removeLastReceivedMessage(Message message){
        receivedMessages.remove(message);
    }

    public List<Message> combineMessages(){
        List<Message> combinedMessages = new ArrayList<>();
        combinedMessages.addAll(receivedMessages);
        combinedMessages.addAll(sentMessages);
        return combinedMessages;
    }

    @Override
    public Iterator<Message> iterator() {
        return combineMessages().iterator();
    }

    public Iterator<Message> iterator(User userToSearchWith) {
        return new SearchMessagesByUser(combineMessages().iterator(), userToSearchWith);
    }

}