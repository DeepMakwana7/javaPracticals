package app;

import model.Item;
import service.ItemService;
import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        runTests();

        ItemService service = new ItemService();
        service.addItem(new Item("Laptop"));
        System.out.println("App running. Current item count: " + service.getCount());
    }

    public static void runTests()
    {
        testAddAndCount();
        testSaveAndReload();
        System.out.println("All package and persistence tests passed successfully!\n");
    }

    static void testAddAndCount()
    {
        ItemService service = new ItemService();
        assert service.getCount() == 0;
        service.addItem(new Item("Pen"));
        assert service.getCount() == 1;
    }

    static void testSaveAndReload()
    {
        String testFile = "test_items.ser";
        ItemService service1 = new ItemService();
        service1.addItem(new Item("Book"));
        service1.addItem(new Item("Notebook"));

        try
        {
            service1.saveToFile(testFile);

            ItemService service2 = new ItemService();
            service2.loadFromFile(testFile);

            assert service2.getCount() == 2;
            assert service2.getItems().get(0).name.equals("Book");
            assert service2.getItems().get(1).name.equals("Notebook");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            new File(testFile).delete();
        }
    }
}