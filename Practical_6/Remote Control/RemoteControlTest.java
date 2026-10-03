
interface Switchable 
{
    void on();
    void off();
    
    default void toggle() 
    {
        System.out.println("Toggling device state...");
    }
}

class Fan implements Switchable 
{
    private boolean isOn = false;

    @Override
    public void on()
    {
        isOn = true;
        System.out.println("Fan is now ON.");
    }

    @Override
    public void off() 
    {
        isOn = false;
        System.out.println("Fan is now OFF.");
    }

    @Override
    public void toggle() 
    {
        if (isOn) 
        {
            off();
        } 
        else 
        {
            on();
        }
    }
}

class Light implements Switchable 
{
    private boolean isOn = false;

    @Override
    public void on() 
    {
        isOn = true;
        System.out.println("Light is now ON.");
    }

    @Override
    public void off() 
    {
        isOn = false;
        System.out.println("Light is now OFF.");
    }

    @Override
    public void toggle() 
    {
        if (isOn) 
        {
            off();
        } 
        else 
        {
            on();
        }
    }
}

@FunctionalInterface
interface OperationRule 
{
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControlTest 
{
    public static void main(String[] args) 
    {
        Switchable[] devices = { new Fan(), new Light() };

        System.out.println("--- Toggling Devices ---");
        for (Switchable device : devices) 
        {
            device.toggle();
        }

        OperationRule anonymousRule = new OperationRule() 
        {
            @Override
            public boolean maySwitchOn(Switchable device, int hour) 
            {
                if (device instanceof Fan) 
                {
                    return hour >= 6 && hour <= 22;
                }
                return true;
            }
        };

        OperationRule lambdaRule = (device, hour) -> {
            if (device instanceof Light) 
            {
                return hour >= 18 || hour < 6;
            }
            return true;
        };

        System.out.println("Testing Permission Rules");
        int currentHour = 20;
        
        System.out.println("Anonymous Rule (Fan at " + currentHour + ":00): " 
            + anonymousRule.maySwitchOn(devices[0], currentHour));
            
        System.out.println("Lambda Rule (Light at " + currentHour + ":00): " 
            + lambdaRule.maySwitchOn(devices[1], currentHour));
    }
}