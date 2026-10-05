class SharedCounter 
{
    private int count = 0;
    public synchronized void increment() 
    {
        count++;
    }

    public int getCount() 
    {
        return count;
    }
}

class MyRunnable implements Runnable 
{
    private SharedCounter counter;

    public MyRunnable(SharedCounter counter) 
    {
        this.counter = counter;
    }

    @Override
    public void run() 
    {
        for (int i = 0; i < 1000; i++) 
        {
            counter.increment();
        }
    }
}

public class driver 
{
    public static void main(String[] args) throws InterruptedException 
    {
        SharedCounter counter = new SharedCounter();

        Thread t1 = new Thread(new MyRunnable(counter));
        Thread t2 = new Thread(new MyRunnable(counter));

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Final Count: " + counter.getCount());
    }
}