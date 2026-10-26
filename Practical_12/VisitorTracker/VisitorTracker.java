import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

class VisitorTracker
{
    public static void main(String[] args)
    {
        List<String> logins = Arrays.asList("alice", "bob", "alice", "charlie", "david", "bob", "eve", "charlie");

        Set<String> uniqueSet = new HashSet<>(logins);
        Set<String> orderedSet = new LinkedHashSet<>(logins);

        System.out.println("Unique count: " + uniqueSet.size());
        System.out.println("First three distinct names:");

        int count = 0;
        for (String name : orderedSet)
        {
            if (count >= 3)
            {
                break;
            }

            System.out.println(name);
            count++;
        }
    }
}