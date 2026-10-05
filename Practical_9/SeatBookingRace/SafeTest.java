class SafeTheatre 
{
    private int seatsLeft = 5;
    private int successfulBookings = 0;

    public synchronized boolean bookSeat() 
    {
        if (seatsLeft > 0) 
        {
            try { Thread.sleep(10); } catch (InterruptedException e) {}
            seatsLeft--;
            successfulBookings++;
            return true;
        }
        return false;
    }

    public int getSuccessfulBookings() 
    {
        return successfulBookings;
    }
}

public class SafeTest 
{
    public static void main(String[] args) throws InterruptedException 
    {
        SafeTheatre theater = new SafeTheatre();
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) 
        {
            threads[i] = new Thread(() -> {
                theater.bookSeat();
            });
            threads[i].start();
        }

        for (Thread t : threads) 
        {
            t.join();
        }

        System.out.println("Successful bookings: " + theater.getSuccessfulBookings());
    }
}