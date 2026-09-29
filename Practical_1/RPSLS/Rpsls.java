import java.util.Scanner;
enum Move{Rock,Paper,Scissors,Lizard,Spock}
public class Rpsls {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String a1="";
        String b1="";

        int awin=0;
        int bwin=0;

        for(int i=0;i<5;i++)
        {
            System.out.print("Enter Move A : ");
            a1 = sc.next();
            System.out.print("Enter Move B : ");
            b1 = sc.next();


            Move a = Move.valueOf(a1);
            Move b = Move.valueOf(b1);
            System.out.println("A chose : "+a1);
            System.out.println("B chose : "+b1);

            Winner w = new Winner();

            int winner = w.decideWinner(a,b);
            if(winner==1)
            {
                awin++;
                System.out.println("The Winner is A : "+a1);
                System.out.println("A : "+awin);
                System.out.println("B : "+bwin);
            }
            else if(winner==0)
            {
                awin++;
                bwin++;
                System.out.println("It's a Tie! Both Player gets the point! ");
                System.out.println("A : "+awin);
                System.out.println("B : "+bwin);
            }
            else
            {
                bwin++;
                System.out.println("The Winner is B : "+b1);
                System.out.println("A : "+awin);
                System.out.println("B : "+bwin);
            }

        }

        if(awin>bwin)
        {
            System.out.println("The winner is A!");
        }
        else if(awin==bwin)
        {
            System.out.println("The game is a Tie!");
        }
        else
        {
            System.out.println("The winner is B!");
        }
        System.out.println("Final Scores : ");
        System.out.println("A : "+awin);
        System.out.println("B : "+bwin);

        System.out.println();


    }
}