import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

class LogAnalyzer
{
    public static void main(String[] args)
    {
        try
        {
            Path file1 = Paths.get("log1.txt");
            Path file2 = Paths.get("log2.txt");

            Files.write(file1, Arrays.asList("INFO: System started", "ERROR: Database connection failed", "INFO: Retrying..."));
            Files.write(file2, Arrays.asList("DEBUG: Loading cache", "ERROR: Timeout occurred", "INFO: Shutdown complete"));

            List<Path> files = Arrays.asList(file1, file2);
            String keyword = "ERROR";

            int totalLines = 0;
            int keywordCount = 0;

            for (Path file : files)
            {
                long size = Files.size(file);
                System.out.println("File: " + file.getFileName() + " | Size: " + size + " bytes");

                List<String> lines = Files.readAllLines(file);
                for (String line : lines)
                {
                    totalLines++;
                    if (line.contains(keyword))
                    {
                        keywordCount++;
                    }
                }
            }

            System.out.println("Total lines across all files: " + totalLines);
            System.out.println("Total lines containing '" + keyword + "': " + keywordCount);

            Files.deleteIfExists(file1);
            Files.deleteIfExists(file2);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}