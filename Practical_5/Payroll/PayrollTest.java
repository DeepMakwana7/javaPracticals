abstract class Employee 
{
    protected String name;
    protected int id;

    public Employee(String name, int id) 
    {
        this.name = name;
        this.id = id;
    }
    public abstract double monthlySalary();

    public String getName() 
    {
        return name;
    }

    public int getId() 
    {
        return id;
    }
}

class FullTime extends Employee 
{
    private double fixedSalary;

    public FullTime(String name, int id, double fixedSalary) 
    {
        super(name, id);
        this.fixedSalary = fixedSalary;
    }

    @Override
    public double monthlySalary() 
    {
        return fixedSalary;
    }
}

class PartTime extends Employee 
{
    private double hourlyRate;
    private int hoursWorked;

    public PartTime(String name, int id, double hourlyRate, int hoursWorked) 
    {
        super(name, id);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double monthlySalary() 
    {
        return hourlyRate * hoursWorked;
    }
}

class Intern extends Employee 
{
    private double stipend;

    public Intern(String name, int id, double stipend) 
    {
        super(name, id);
        this.stipend = stipend;
    }

    @Override
    public double monthlySalary() 
    {
        return stipend;
    }
}

public class PayrollTest 
{
    public static void main(String[] args) 
    {
        Employee[] employees = 
        {
            new FullTime("Alice", 101, 5500.0),
            new PartTime("Bob", 102, 30.0, 80),
            new Intern("Charlie", 103, 1200.0),
            new FullTime("Diana", 104, 6200.0)
        };

        double totalPayroll = 0.0;

        for (Employee emp : employees) 
        {
            double salary = emp.monthlySalary();
            totalPayroll += salary;

            System.out.println("ID: " + emp.getId() + " | Name: " + emp.getName() + " | Salary: $" + salary);

            if (emp instanceof Intern) {
                System.out.println("  -> [Note: Seasonal intern program participant]");
            }

            System.out.println("Running Total: $" + totalPayroll);
            System.out.println("----------------------------------------");
        }

        System.out.println("Final Total Monthly Payroll: $" + totalPayroll);
    }
}