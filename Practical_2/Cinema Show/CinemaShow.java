public class CinemaShow {
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked=0;

    CinemaShow(String title)
    {
        this(title,100);
    }

    CinemaShow(String title,int capacity)
    {
        this.title=title;
        this.capacity=capacity;
        seatsAvailable=capacity;
    }

    Boolean book(int n)
    {
        if(n<=seatsAvailable)
        {
            seatsAvailable=seatsAvailable-n;
            totalBooked=totalBooked+n;
            System.out.println("Tickets booked : "+n);
            System.out.println("Tickets remain : "+seatsAvailable);
            return true;
        }
        System.out.println("Cant book more than available seat!");
        return false;
    }

    Boolean cancel(int n)
    {
        if(n>0)
        {
            if(n+seatsAvailable<=capacity)
            {
                seatsAvailable=seatsAvailable+n;
                System.out.println("Tickets Added : "+n);
                System.out.println("Updated Tickets : "+seatsAvailable);
                return true;
            }
        }
        System.out.println("Cancellation cant be greater than total seats.");
        return false;
    }

    int getSeatsAvailable()
    {
        return seatsAvailable;
    }
    int getTotalBooked()
    {
        return totalBooked;
    }
}

class Main
{
    public static void main(String[] args) {
        CinemaShow c = new CinemaShow("Charusat",100);
        c.book(100);
        c.cancel(40);
        c.book(30);
    }
}