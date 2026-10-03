import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateFiller {
    private String template;
    private String[] keys;
    private String[] values;

    public TemplateFiller(String template, String[] keys, String[] values) {
        this.template = template;
        this.keys = keys;
        this.values = values;
    }

    public String fill() 
    {
        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(template);
        
        StringBuilder sb = new StringBuilder();
        
        while (matcher.find()) {
            String placeholderKey = matcher.group(1); 
            String replacement = "[?]"; 
            
            for (int i = 0; i < keys.length; i++) 
            {
                if (keys[i].equals(placeholderKey)) 
                {
                    if (i < values.length) 
                    {
                        replacement = values[i];
                    }
                    break;
                }
            }
            
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        
        return sb.toString();
    }
}