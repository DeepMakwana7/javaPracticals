import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank
{
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength
{
    int value();
}

class SignupForm
{
    @NotBlank
    @MaxLength(10)
    String username;

    @NotBlank
    String email;

    SignupForm(String username, String email)
    {
        this.username = username;
        this.email = email;
    }
}

class FormValidator
{
    public static List<String> validate(Object object) throws IllegalAccessException
    {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields())
        {
            field.setAccessible(true);
            Object value = field.get(object);

            if (field.isAnnotationPresent(NotBlank.class))
            {
                if (value == null || String.valueOf(value).trim().isEmpty())
                {
                    errors.add(field.getName() + " cannot be blank");
                }
            }

            if (field.isAnnotationPresent(MaxLength.class))
            {
                MaxLength maxLength = field.getAnnotation(MaxLength.class);
                if (value != null && String.valueOf(value).length() > maxLength.value())
                {
                    errors.add(field.getName() + " exceeds max length of " + maxLength.value());
                }
            }
        }

        return errors;
    }

    public static void main(String[] args) throws IllegalAccessException
    {
        SignupForm form = new SignupForm("verylongusername", "");
        List<String> errors = validate(form);

        for (String error : errors)
        {
            System.out.println(error);
        }
    }
}