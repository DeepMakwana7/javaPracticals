import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column
{
    String name();
}

class UserRecord
{
    @Column(name = "user_name")
    String name;

    @Column(name = "user_age")
    String age;

    @Column(name = "user_email")
    String email;
}

class CsvMapper
{
    public static <T> T populate(Class<T> clazz, String[] headers, String[] values) throws Exception
    {
        T instance = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields())
        {
            if (field.isAnnotationPresent(Column.class))
            {
                Column column = field.getAnnotation(Column.class);
                String columnName = column.name();

                int index = -1;
                for (int i = 0; i < headers.length; i++)
                {
                    if (headers[i].equals(columnName))
                    {
                        index = i;
                        break;
                    }
                }

                if (index != -1 && index < values.length)
                {
                    field.setAccessible(true);
                    field.set(instance, values[index]);
                }
            }
        }

        return instance;
    }

    public static void main(String[] args) throws Exception
    {
        String[] headers = {"user_name", "user_age"};
        String[] values = {"Alice", "25"};

        UserRecord user = populate(UserRecord.class, headers, values);

        System.out.println("Name: " + user.name);
        System.out.println("Age: " + user.age);
        System.out.println("Email: " + user.email);
    }
}