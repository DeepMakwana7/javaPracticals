package service;

import model.Item;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemService
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

    public List<Item> getItems()
    {
        return items;
    }

    public void saveToFile(String filename) throws IOException
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            oos.writeObject(items);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            items = (List<Item>) ois.readObject();
        }
    }
}