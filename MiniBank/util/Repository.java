package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import model.annotation.Id;

public class Repository<T> {
    private final HashMap<String, T> entities = new HashMap<>();

    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

        String id = extractId(entity);
        entities.put(id, entity);
        return entity;
    }

    @SafeVarargs
    public final ArrayList<T> saveAll(T... items) {
        if (items == null) {
            throw new IllegalArgumentException("Items cannot be null.");
        }
        ArrayList<T> saved = new ArrayList<>(items.length);
        for (T item : items) {
            saved.add(save(item));
        }
        return saved;
    }

    public T findById(String id) {
        return entities.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(entities.values());
    }

    public boolean delete(String id) {
        return entities.remove(id) != null;
    }

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Values must contain at least one number.");
        }
        double total = 0;
        for (N value : values) {
            if (value == null) {
                throw new IllegalArgumentException("Values cannot contain null.");
            }
            total += value.doubleValue();
        }
        return total / values.length;
    }

    private String extractId(T entity) {
        for (Class<?> type = entity.getClass(); type != null; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Id.class)) {
                    continue;
                }

                try {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if (!(value instanceof String id) || id.isBlank()) {
                        throw new IllegalArgumentException(
                                "@Id field must contain a non-empty String: " + field.getName());
                    }
                    return id;
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(
                            "Cannot read @Id field " + field.getName(), e);
                } catch (SecurityException e) {
                    throw new IllegalStateException(
                            "Access denied for @Id field " + field.getName(), e);
                }
            }
        }

        throw new IllegalArgumentException(
                "Entity type " + entity.getClass().getName() + " has no @Id field.");
    }
}
