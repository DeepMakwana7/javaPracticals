import java.util.Scanner;

enum Coin{ONE,TWO,FIVE,TEN}

public class VendingMachine {
    public static void main(String[] args) {
        int price=15;
        int paid=0;

        Scanner sc = new Scanner(System.in);
        String input;

        while(paid<15)
        {
            System.out.print("Enter Coin : ");
            input = sc.next().toUpperCase();
            Coin coin = Coin.valueOf(input);

            int instance = switch(coin)
            {
                case ONE->1;
                case TWO->2;
                case FIVE->5;
                case TEN->10;
            };
            paid=paid+instance;
            System.out.println("Item Price : "+price);
            System.out.println("Money Paid : "+paid);
            System.out.println("To Be Paid : "+(price-paid));


        }

        System.out.println("Paid : "+paid+ " . Change : "+(paid-price));


    }

}
