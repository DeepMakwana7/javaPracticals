
interface Urgent {}

@FunctionalInterface
interface Notifier 
{
    void send(String message);
}

class UrgentNotifier implements Notifier, Urgent 
{
    private final Notifier notifier;

    public UrgentNotifier(Notifier notifier) 
    {
        this.notifier = notifier;
    }

    @Override
    public void send(String message) 
    {
        notifier.send(message);
    }
}

public class NotificationTest 
{
    public static void main(String[] args) 
    {
        Notifier emailSender = (msg) -> System.out.println("Sending Email: " + msg);
        Notifier smsSender = (msg) -> System.out.println("Sending SMS: " + msg);

        Notifier urgentSmsSender = new UrgentNotifier(smsSender);

        Notifier[] notifiers = 
        {
            emailSender,
            urgentSmsSender
        };

        String broadcastMessage = "System update scheduled for midnight.";

        for (Notifier n : notifiers) 
        {
            n.send(broadcastMessage);

            if (n instanceof Urgent) 
            {
                System.out.println("  -> [Urgent Priority: Resending notification]");
                n.send(broadcastMessage);
            }
            System.out.println("---------------------------------------------");
        }
    }
}