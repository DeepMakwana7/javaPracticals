public class ChatFilter {
    public void generate(String[] l,String k)
    {
        int count=0;
        for(String lines : l)
        {
            String[] parts = lines.split(" ",3);
            if(parts.length<3)
            {
                continue;
            }
            String time = parts[0];
            String name = parts[1];
            String message = parts[2];
            if(message.toLowerCase().contains(k.toLowerCase()))
            {
                count++;
                System.out.println(time+" "+name+" : "+message);
            }
        }
        System.out.println("Count : "+count);
    }
}