import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Leaderboard
{
    public static void main(String[] args)
    {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 85);
        scores.put("Bob", 92);
        scores.put("Charlie", 85);
        scores.put("David", 95);
        scores.put("Eve", 92);

        List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

        list.sort((a, b) -> 
        {
            int scoreCompare = b.getValue().compareTo(a.getValue());
            if (scoreCompare != 0)
            {
                return scoreCompare;
            }
            return a.getKey().compareTo(b.getKey());
        });

        int n = 3;
        System.out.println("Top " + n + " Leaderboard:");
        
        for (int i = 0; i < Math.min(n, list.size()); i++)
        {
            Map.Entry<String, Integer> entry = list.get(i);
            System.out.println((i + 1) + ". " + entry.getKey() + " - " + entry.getValue());
        }
    }
}