import java.util.Scanner;
public class PasswordChecker {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n=0;
        System.out.print("Enter no of passwords to check : ");
        n = sc.nextInt();

        for(int i=0;i<n;i++)
        {
            String pswd;
            System.out.print("Enter a Password for a check : ");
            pswd = sc.next();

            Driver d = new Driver();
            d.strength(pswd);
        }

    }
}
