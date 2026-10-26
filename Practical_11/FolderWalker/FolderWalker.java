import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class FolderWalker
{
    public static void main(String[] args)
    {
        Path startDir = Paths.get(".");
        Path reportFile = Paths.get("report.txt");
        List<String> reportLines = new ArrayList<>();

        reportLines.add("File Report");
        reportLines.add("--------------------------------------------------");

        try (Stream<Path> stream = Files.walk(startDir))
        {
            stream.filter(Files::isRegularFile).forEach(path ->
            {
                try
                {
                    BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                    String line = String.format("Name: %s | Size: %d bytes | Modified: %s",
                            path.getFileName(), attrs.size(), attrs.lastModifiedTime());
                    reportLines.add(line);
                }
                catch (IOException e)
                {
                    reportLines.add("Error reading: " + path);
                }
            });

            Files.write(reportFile, reportLines);
            System.out.println("Report generated successfully.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}