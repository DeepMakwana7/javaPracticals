public class Driver {
    public static void main(String[] args) 
    {
        String template = "Dear {name}, order {id} ships {date}.";
        String[] names = {"name", "id"};
        String[] values = {"Riya", "A07"};
        
        TemplateFiller filler = new TemplateFiller(template, names, values);
        
        String result = filler.fill();
        
        System.out.println(result);
    }
}