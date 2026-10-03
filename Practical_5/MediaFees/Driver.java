
abstract class MediaItem 
{
    protected String title;
    protected int daysLate;

    public MediaItem(String title, int daysLate) 
    {
        this.title = title;
        this.daysLate = Math.max(0, daysLate);
    }

    public abstract double computeLateFee();

    public String getTitle() 
    {
        return title;
    }

    public int getDaysLate() 
    {
        return daysLate;
    }
}

class Book extends MediaItem 
{
    public Book(String title, int daysLate) 
    {
        super(title, daysLate);
    }

    @Override
    public double computeLateFee() 
    {
        return daysLate * 0.50;
    }
}

class DVD extends MediaItem 
{
    public DVD(String title, int daysLate) 
    {
        super(title, daysLate);
    }

    @Override
    public double computeLateFee() 
    {
        if (daysLate == 0) return 0.0;
        return (daysLate * 1.50) + 2.00;
    }
}

class VideoGame extends MediaItem 
{
    public VideoGame(String title, int daysLate) 
    {
        super(title, daysLate);
    }

    @Override
    public double computeLateFee() 
    {
        double fee = daysLate * 2.50;
        return Math.min(fee, 30.0);
    }
}

public class Driver 
{
    public static void main(String[] args) 
    {
        MediaItem[] returnedBatch = {
            new Book("Effective Java", 4),         
            new DVD("Interstellar", 3),             
            new VideoGame("Cyberpunk 2077", 20),  
            new Book("Clean Code", 0)             
        };

        double totalBatchFees = 0.0;

        System.out.println("--- Processing Return Batch ---");
        for (MediaItem item : returnedBatch) 
        {
            double fee = item.computeLateFee();
            totalBatchFees += fee;

            System.out.println(item.getClass().getSimpleName() + 
                " [\"" + item.getTitle() + "\"] | Days Late: " + item.getDaysLate() + 
                " | Fee: $" + String.format("%.2f", fee));
        }

        System.out.println("---------------------------------------------");
        System.out.println("Total Batch Late Fees: $" + String.format("%.2f", totalBatchFees));
    }
}