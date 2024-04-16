import java.util.Iterator;
import java.util.List;

public class Main {
    private static final ChatServer chatServer = new ChatServer();

    public static void main(String[] args) {
        System.out.println("************** WELCOME TO THE CHAT ROOM **************");
        User user1 = new User("Gabriel", chatServer);
        System.out.println(user1.getUsername() + " has joined the chat room.");
        User user2 = new User("Mikael", chatServer);
        System.out.println(user2.getUsername() + " has joined the chat room.");
        User user3 = new User("David", chatServer);
        System.out.println(user3.getUsername() + " has joined the chat room.");

        System.out.println("\n********************************************************");
        chatServer.sendMessage(new Message(user1, List.of(user2), "Are we destroying the world tonight?"));
        chatServer.sendMessage(new Message(user1, List.of(user3), "We are waiting for the order."));
        chatServer.sendMessage(new Message(user3, List.of(user1), "God have mercy..."));
        System.out.println("\n********************************************************");
        System.out.println("************** BLOCKING OPERATION **************");
        user2.blockerUsers(user1);
        chatServer.sendMessage(new Message(user1, List.of(user2, user3), "WHAT'S HAPPENING?!"));

        System.out.println("\n************** MESSAGE RECALL **************");
        System.out.println("Gabriel unsent the last message.");
        chatServer.undoLastMessage(user1);
        System.out.println("Gabriel's last message: " + user1.getChatHistory().getLastSentMessages());

        // ITERATING OVER GABRIEL'S MESSAGES
        System.out.println("\n************** ITERATION OVER MESSAGES **************");
        System.out.println("Gabriel's full chat history:");
        Iterator<Message> allMessagesIterator = user1.iterator();
        while (allMessagesIterator.hasNext()) {
            System.out.println(allMessagesIterator.next());
        }

        System.out.println("\nMikael's full chat history:");
        allMessagesIterator = user2.iterator();
        while (allMessagesIterator.hasNext()) {
            System.out.println(allMessagesIterator.next());
        }
        System.out.println("\n********************************************************");

        chatServer.unregisterUser(user1);
        chatServer.sendMessage(new Message(user3, List.of(user1), "WHERE ARE YOU!!"));
    }
}
