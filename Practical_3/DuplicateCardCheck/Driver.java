import java.util.Scanner;

public class Driver 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of cards to check: ");
        int n = sc.nextInt();
        sc.nextLine();

        Card[] deck = new Card[n];
        int size = 0;
        boolean found = false;

        for (int i = 0; i < n; i++) 
        {
            System.out.print("Enter rank for card " + (i + 1) + ": ");
            String rank = sc.nextLine();

            System.out.print("Enter suit for card " + (i + 1) + ": ");
            String suit = sc.nextLine();

            Card current = new Card(rank, suit);

            boolean duplicate = false;
            for (int j = 0; j < size; j++) 
            {
                if (current.equals(deck[j])) 
                {
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) 
            {
                System.out.println("Duplicate found: " + current);
                found = true;
                break;
            } 
            else 
            {
                deck[size] = current;
                size++;
            }
        }

        if (!found) 
        {
            System.out.println("No duplicates found.");
        }

        sc.close();
    }
}