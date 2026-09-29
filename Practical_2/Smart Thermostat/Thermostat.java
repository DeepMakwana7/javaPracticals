
public class Thermostat {
    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount=0;


    Thermostat(String l,int startTemp)
    {
        location=l;
        if(startTemp>=MIN || startTemp<=MAX)
        {
            temperature=startTemp;
        }
        else
        {
            temperature = 22;
        }
        activeCount++;
    }

    void raise()
    {
        if(temperature<MAX)
        {
            temperature++;
            getTemperature();
        }
        else
        {
            System.out.println("Already at Maximum (30)");
        }
    }
    void lower()
    {
        if(temperature>MIN)
        {
            temperature--;
            getTemperature();
        }
        else
        {
            System.out.println("Already at Minimum (16)");
        }
    }

    void getTemperature()
    {
        System.out.println("Current Temperature : "+temperature);
    }
    static void getActiveCount()
    {
        System.out.println("Current Active Count : "+activeCount);
    }
}

class Main
{
    public static void main(String[] args)
    {
        Thermostat t1 = new Thermostat("First",20);
        Thermostat t2 = new Thermostat("First",40);

        System.out.println("For first thermostat.");
        t1.getTemperature();
        for(int i=0;i<10;i++)
        {
            System.out.println("Loop No : "+(i+1));
            t1.raise();
        }
        System.out.println("Raise() loop completed.");
        for(int i=0;i<20;i++)
        {
            System.out.println("Loop No : "+(i+1));
            t1.lower();
        }
        System.out.println("Lower() loop completed.");


        System.out.println("For second thermostat.");
        t1.getTemperature();
        for(int i=0;i<10;i++)
        {
            System.out.println("Loop No : "+(i+1));
            t1.raise();
        }
        System.out.println("Raise() loop completed.");
        for(int i=0;i<20;i++)
        {
            System.out.println("Loop No : "+(i+1));
            t1.lower();
        }
        System.out.println("Lower() loop completed.");

        Thermostat.getActiveCount();

    }


}
