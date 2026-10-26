import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class UserRecord implements Serializable
{
    private static final long serialVersionUID = 1L;

    String username;
    transient String secretToken;

    UserRecord(String username, String secretToken)
    {
        this.username = username;
        this.secretToken = secretToken;
    }
}

class SerializationDemo
{
    public static void main(String[] args)
    {
        String fileName = "users.ser";

        UserRecord[] usersToWrite = 
        {
            new UserRecord("Alice", "token_123"),
            new UserRecord("Bob", "token_456")
        };

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            oos.writeObject(usersToWrite);
            System.out.println("Objects written to file successfully.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        UserRecord[] usersRead = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            usersRead = (UserRecord[]) ois.readObject();
            System.out.println("Objects read from file successfully.\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (usersRead != null)
        {
            for (UserRecord user : usersRead)
            {
                System.out.println("Username: " + user.username);
                System.out.println("Secret Token (transient): " + user.secretToken);
                System.out.println("-------------------");
            }
        }
    }
}