import java.util.Scanner;

public class Driver 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of points: ");
        int n = sc.nextInt();

        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) 
        {
            System.out.print("Enter x and y for point " + (i + 1) + ": ");
            int x = sc.nextInt();
            int y = sc.nextInt();
            points[i] = new Point(x, y);
        }

        int count = 0;

        for (int i = 0; i < points.length; i++) 
        {
            boolean found = false;
            for (int j = 0; j < i; j++) 
            {
                if (points[i].equals(points[j])) 
                {
                    found = true;
                    break;
                }
            }
            if (!found) 
            {
                count++;
            }
        }

        System.out.println("Distinct: " + count);
        sc.close();
    }
}