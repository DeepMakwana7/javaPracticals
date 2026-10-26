import java.util.ArrayList;
import java.util.List;

class OutOfStockException extends Exception
{
    private final int shortfall;

    OutOfStockException(String message, int shortfall)
    {
        super(message);
        this.shortfall = shortfall;
    }

    public int getShortfall()
    {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception
{
    InvalidQuantityException(String message)
    {
        super(message);
    }
}

class StockRequest
{
    String item;
    int qty;

    StockRequest(String item, int qty)
    {
        this.item = item;
        this.qty = qty;
    }
}

class Warehouse
{
    private final String itemName;
    private int availableQty;

    Warehouse(String itemName, int availableQty)
    {
        this.itemName = itemName;
        this.availableQty = availableQty;
    }

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException
    {
        if (qty <= 0)
        {
            throw new InvalidQuantityException("Quantity must be greater than zero");
        }

        if (!itemName.equalsIgnoreCase(item))
        {
            throw new OutOfStockException("Item not found", qty);
        }

        if (qty > availableQty)
        {
            int shortfall = qty - availableQty;
            throw new OutOfStockException("Insufficient stock", shortfall);
        }

        availableQty -= qty;
    }
}

class InventoryProcessor
{
    public static void main(String[] args)
    {
        Warehouse warehouse = new Warehouse("Laptop", 10);

        List<StockRequest> requests = new ArrayList<>();
        requests.add(new StockRequest("Laptop", 3));
        requests.add(new StockRequest("Laptop", 0));
        requests.add(new StockRequest("Laptop", 15));
        requests.add(new StockRequest("Phone", 2));

        for (StockRequest request : requests)
        {
            try
            {
                warehouse.issue(request.item, request.qty);
                System.out.println("Success: Issued " + request.qty + " of " + request.item);
            }
            catch (InvalidQuantityException e)
            {
                System.out.println("Failed (" + request.item + ", qty: " + request.qty + "): " + e.getMessage());
            }
            catch (OutOfStockException e)
            {
                System.out.println("Failed (" + request.item + ", qty: " + request.qty + "): " + e.getMessage() + " (Shortfall: " + e.getShortfall() + ")");
            }
        }
    }
}