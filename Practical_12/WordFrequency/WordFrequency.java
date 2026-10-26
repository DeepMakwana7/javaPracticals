import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class WordFrequency
{
    public static void main(String[] args)
    {
        String paragraph = "Java is a popular programming language and Java is widely used for building applications and web development";
        String[] words = paragraph.toLowerCase().split("\\W+");

        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String word : words)
        {
            if (!word.isEmpty())
            {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(frequencyMap.entrySet());

        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<String, Integer> entry : list)
        {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}