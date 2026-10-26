package model;

import java.io.Serializable;

public class Item implements Serializable
{
    private static final long serialVersionUID = 1L;
    public String name;

    public Item(String name)
    {
        this.name = name;
    }
}