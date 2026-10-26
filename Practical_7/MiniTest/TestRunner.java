import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run
{
}

class SampleTests
{
    @Run
    void testOne()
    {
        System.out.println("Executing test one");
    }

    void testTwo()
    {
        System.out.println("Skipped test");
    }

    @Run
    void testThree()
    {
        System.out.println("Executing test three");
    }
}

class TestRunner
{
    public static void main(String[] args) throws Exception
    {
        Class<?> clazz = SampleTests.class;
        Object instance = clazz.getDeclaredConstructor().newInstance();
        int count = 0;

        for (Method method : clazz.getDeclaredMethods())
        {
            if (method.isAnnotationPresent(Run.class))
            {
                method.setAccessible(true);
                method.invoke(instance);
                count++;
            }
        }

        System.out.println("Total tests ran: " + count);
    }
}