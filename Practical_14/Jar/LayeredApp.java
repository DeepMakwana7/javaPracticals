import java.util.ArrayList;
import java.util.List;

class Item
{
    String name;

    Item(String name)
    {
        this.name = name;
    }
}

class ItemService
{
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item)
    {
        if (item == null || item.name == null || item.name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Invalid item");
        }
        items.add(item);
    }

    public int getCount()
    {
        return items.size();
    }
}

class LayeredApp
{
    public static void main(String[] args)
    {
        testAddIncreasesCount();
        testInvalidActionThrows();
        
        System.out.println("All tests passed successfully!");
    }

    static void testAddIncreasesCount()
    {
        ItemService service = new ItemService();
        assert service.getCount() == 0 : "Initial count should be 0";
        
        service.addItem(new Item("Pen"));
        assert service.getCount() == 1 : "Count should be 1 after adding an item";
    }

    static void testInvalidActionThrows()
    {
        ItemService service = new ItemService();
        boolean threwException = false;
        
        try
        {
            service.addItem(new Item(""));
        }
        catch (IllegalArgumentException e)
        {
            threwException = true;
        }
        
        assert threwException : "Should have thrown IllegalArgumentException for empty item name";
    }
}