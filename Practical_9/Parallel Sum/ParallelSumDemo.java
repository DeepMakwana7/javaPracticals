import java.util.Arrays;

public class ParallelSumDemo 
{
    public static void main(String[] args) throws InterruptedException 
    {
        int size = 50_000_000;
        int[] numbers = new int[size];
        Arrays.fill(numbers, 1);
        
        long expectedSum = size; 

        int numThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("--- Parallel Array Sum Demo ---");
        System.out.println("Array size: " + size);
        System.out.println("Using " + numThreads + " concurrent threads.\n");

        Thread[] threads = new Thread[numThreads];
        long[] partialSums = new long[numThreads]; 

        int chunkSize = size / numThreads;

        long startTime = System.nanoTime();

        for (int i = 0; i < numThreads; i++) 
        {
            final int threadIndex = i;
            int start = i * chunkSize;
        
            int end = (i == numThreads - 1) ? size : start + chunkSize;

            threads[i] = new Thread(() -> {
                long localSum = 0;
                for (int j = start; j < end; j++) 
                {
                    localSum += numbers[j];
                }
                partialSums[threadIndex] = localSum; 
            });
            threads[i].start();
        }

        for (Thread t : threads) 
        {
            t.join();
        }

        long finalTotal = 0;
        for (long sum : partialSums) 
        {
            finalTotal += sum;
        }

        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime);

        System.out.println("Expected Sum: " + expectedSum);
        System.out.println("Final Sum:    " + finalTotal);
        System.out.println("Time Taken:   " + durationMs + " ms");
        
        if (finalTotal == expectedSum) 
        {
            System.out.println("\nStatus: SUCCESS! Exact match every time with maximum performance.");
        } 
        else 
        {
            System.out.println("\nStatus: ERROR! Mismatch detected.");
        }
    }
}