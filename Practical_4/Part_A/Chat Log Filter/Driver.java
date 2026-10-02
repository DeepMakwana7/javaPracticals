import java.util.Scanner;
public class Driver {
    public static void main(String[] args) {

        String[] logs = {
                "13:04 Deep Good Morning Everyone",
                "24:45 Abhishek Hello",
                "23:09 Virat World cup 2027",
                "malicious message"
        };

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter keyword : ");
        String kw = sc.next();
        ChatFilter cf = new ChatFilter();
        cf.generate(logs,kw);
    }
}