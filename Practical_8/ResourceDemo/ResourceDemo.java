class DatabaseConnection implements AutoCloseable
{
    DatabaseConnection()
    {
        System.out.println("Database connection opened.");
    }

    public void executeQuery()
    {
        System.out.println("Executing query...");
        throw new RuntimeException("Original error: Database query failed!");
    }

    @Override
    public void close()
    {
        System.out.println("Database connection closed automatically.");
    }
}

class ResourceDemo
{
    public static void main(String[] args)
    {
        try (DatabaseConnection db = new DatabaseConnection())
        {
            db.executeQuery();
        }
        catch (Exception e)
        {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}