abstract class Shape 
{
    public abstract double area();
}

class Circle extends Shape 
{
    private double radius;

    public Circle(double radius) 
    {
        this.radius = radius;
    }

    @Override
    public double area() 
    {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape 
{
    private double length, width;

    public Rectangle(double length, double width) 
    {
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() 
    {
        return length * width;
    }
}

class Triangle extends Shape 
{
    private double base, height;

    public Triangle(double base, double height) 
    {
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() 
    {
        return 0.5 * base * height;
    }
}

public class ShapeTest 
{
    public static void main(String[] args) 
    {
        Shape[] shapes = 
        {
            new Circle(5.0),
            new Rectangle(4.0, 5.0),
            new Triangle(3.0, 6.0),
            new Circle(2.0)
        };

        double runningTotal = 0.0;
        double maxArea = 0.0;
        Shape largestShape = null;

        for (Shape shape : shapes) 
        {
            double currentArea = shape.area();
            runningTotal += currentArea;

            System.out.println(shape.getClass().getSimpleName() + " Area: " + currentArea);
            System.out.println("Running Total: " + runningTotal);

            if (largestShape == null || currentArea > maxArea) 
            {
                maxArea = currentArea;
                largestShape = shape;
            }
            System.out.println("----------------------------");
        }

        System.out.println("Largest Shape: " + largestShape.getClass().getSimpleName() + " with area: " + maxArea);
    }
}