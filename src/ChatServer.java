import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private List<User> users;

    public ChatServer(){
        users = new ArrayList<>();
    }


    public void sendMessage(Message message) {
        User sender = message.getSender();
        List<User> receivers = new ArrayList<>(message.getReceivers());
        if (!users.contains(sender)) {
            System.out.println("Message cannot be sent: User " + sender.getUsername() + " is not registered in the system.");
            return;
        }
        List<User> validReceivers = new ArrayList<>();
        for (User user : receivers) {
            if (!users.contains(user)) {
                System.out.println("Message delivery failed: " + sender.getUsername() + " cannot send to " + user.getUsername() + " as they are not registered.");
            } else {
                validReceivers.add(user);
            }
        }
        for (User receiver : validReceivers) {
            List<User> blockedAccounts = receiver.getBlockedUsers();
            if (blockedAccounts != null && blockedAccounts.contains(sender)) {
                System.out.println("Message blocked: " + sender.getUsername() + " has been blocked by " + receiver.getUsername() + ".");
            } else {
                sender.sendMessage(message);
                System.out.println("Message sent successfully from " + sender.getUsername() + " to " + receiver.getUsername() + ".");
                receiver.receiveMessage(message);
                System.out.println(receiver.getUsername() + " received a message from " + sender.getUsername() + " at " + message.getTimestamp() + ": '" + message.getTextMessage() + "'");
            }
        }
    }


    public void registerUser(User user) {
        users.add(user);
        System.out.println("User " + user.getUsername() + " has been successfully registered!");
    }

    public void unregisterUser(User user) {
        users.remove(user);
        System.out.println("User " + user.getUsername() + " has been unregistered from the system.");
    }

    public void undoLastMessage(User user) {
        List<Message> sentMessages = user.getChatHistory().getSentMessages();
        if (sentMessages.isEmpty()) {
            System.out.println("Operation failed: User " + user.getUsername() + " has not sent any messages to undo.");
            return;
        }
        Message message = user.getChatHistory().getLastSentMessages();
        user.undoLastSentMessage();
        List<User> receivers = message.getReceivers();
        for (User receiver : receivers) {
            receiver.getChatHistory().removeLastReceivedMessage(message);
        }
        System.out.println("Successfully undid last message sent by " + user.getUsername() + ".");
    }


    public List<User> getUsers(){
        return users;
    }
}