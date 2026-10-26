import java.util.Scanner;

class DivideByZeroException extends Exception
{
    DivideByZeroException(String message)
    {
        super(message);
    }
}

class Calculator
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        boolean success = false;

        while (!success)
        {
            try
            {
                System.out.print("Enter first number: ");
                double num1 = Double.parseDouble(scanner.next());

                System.out.print("Enter operator (+, -, *, /): ");
                char operator = scanner.next().charAt(0);

                System.out.print("Enter second number: ");
                double num2 = Double.parseDouble(scanner.next());

                double result = 0;

                switch (operator)
                {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 == 0)
                        {
                            throw new DivideByZeroException("Cannot divide by zero");
                        }
                        result = num1 / num2;
                        break;
                    default:
                        System.out.println("Error: Invalid operator");
                        continue;
                }

                System.out.println("Result: " + result);
                success = true;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error: Invalid number input entered");
            }
            catch (DivideByZeroException e)
            {
                System.out.println("Error: " + e.getMessage());
            }
            finally
            {
                System.out.println("Log: Attempt processed.\n");
            }
        }
    }
}