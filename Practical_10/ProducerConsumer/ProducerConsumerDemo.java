import java.util.LinkedList;
import java.util.Queue;

class Buffer 
{
    private Queue<Integer> queue = new LinkedList<>();
    private int capacity;

    public Buffer(int capacity) 
    {
        this.capacity = capacity;
    }

    public synchronized void produce(int item) throws InterruptedException 
    {
        while (queue.size() == capacity) 
        {
            wait();
        }
        queue.add(item);
        System.out.println("Produced: " + item);
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException 
    {
        while (queue.isEmpty()) 
        {
            wait();
        }
        int item = queue.poll();
        System.out.println("Consumed: " + item);
        notifyAll();
        return item;
    }
}

public class ProducerConsumerDemo 
{
    public static void main(String[] args) throws InterruptedException 
    {
        Buffer buffer = new Buffer(3);
        int totalItems = 10;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.produce(i);
                    Thread.sleep(100);
                }
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) 
                {
                    buffer.consume();
                    Thread.sleep(150);
                }
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}