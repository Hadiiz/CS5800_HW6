import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User implements Iterable<Message>, IterableByUser {
    private String username;
    private ChatServer chatServer;
    private ChatHistory chatHistory;
    private List<MessageMememto> messageMememtos;
    private List<User> blockedUsers;

    public User(String username, ChatServer chatServer) {
        this.username = username;
        this.chatServer = chatServer;
        chatServer.registerUser(this);
        this.chatHistory = new ChatHistory();
        this.messageMememtos = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
    }

    public void sendMessage(Message message) {
        this.chatHistory.addSentMessage(message);
    }

    public void receiveMessage(Message message) {
        this.chatHistory.addReceivedMessage(message);
    }

    public String getUsername() {
        return username;
    }

    public void undoLastSentMessage() {
        List<Message> sentMessages = chatHistory.getSentMessages();
        if (sentMessages.isEmpty()) {
            System.out.println("No messages to undo for user " + username);
            return;
        }
        Message lastMessage = sentMessages.get(sentMessages.size() - 1);
        chatHistory.removeLastSentMessage(lastMessage);
        MessageMememto messageMememto = lastMessage.saveToMememto();
        lastMessage.restoreFromMememto(messageMememto);
        sentMessages.remove(lastMessage);
    }

    public void blockerUsers(User blockedUser) {
        List<User> users = chatServer.getUsers();
        if (!users.contains(this)) {
            System.out.println("Operation failed: User " + username + " is not registered.");
            return;
        } else if (!users.contains(blockedUser)) {
            System.out.println("Operation failed: User " + blockedUser.getUsername() + " is not registered.");
            return;
        }
        setBlockUsers(blockedUser);
    }

    public void setBlockUsers(User blockedUser) {
        if (blockedUsers.contains(blockedUser)) {
            System.out.println("Action unnecessary: User " + username + " has already blocked user " + blockedUser.getUsername());
        } else {
            blockedUsers.add(blockedUser);
            System.out.println("Action completed: User " + username + " has now blocked user " + blockedUser.getUsername());
        }
    }

    public List<User> getBlockedUsers() {
        return blockedUsers;
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    @Override
    public Iterator<Message> iterator() {
        return chatHistory.iterator();
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return chatHistory.iterator(userToSearchWith);
    }
}
