class Account 
{
    private final String name;
    public Account(String name) 
    { 
        this.name = name; 
    }
    public String getName() 
    { 
        return name; 
    }
}

class SafeBank 
{
    public void transfer(Account from, Account to, int amount) 
    {
        Account firstLock = from;
        Account secondLock = to;

        if (System.identityHashCode(from) > System.identityHashCode(to)) 
        {
            firstLock = to;
            secondLock = from;
        } 
        else if (System.identityHashCode(from) == System.identityHashCode(to)) 
        {
            synchronized (new Object()) 
            { 
                // tie-breaker execution
            }
            return;
        }

        synchronized (firstLock) 
        {
            System.out.println(Thread.currentThread().getName() + " locked " + firstLock.getName());
            try 
            { 
                Thread.sleep(100); 
            } 
            catch (InterruptedException e) {}

            synchronized (secondLock) 
            {
                System.out.println(Thread.currentThread().getName() + " locked " + secondLock.getName());
                System.out.println("Transfer successful!");
            }
        }
    }
}

public class SafeBankDemo 
{
    public static void main(String[] args) 
    {
        SafeBank bank = new SafeBank();
        Account acc1 = new Account("Account-1");
        Account acc2 = new Account("Account-2");

        Thread t1 = new Thread(() -> bank.transfer(acc1, acc2, 100), "Thread-1");
        Thread t2 = new Thread(() -> bank.transfer(acc2, acc1, 100), "Thread-2");

        t1.start();
        t2.start();
    }
}