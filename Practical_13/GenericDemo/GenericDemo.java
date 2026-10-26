class Box<T>
{
    private T value;

    public void set(T value)
    {
        this.value = value;
    }

    public T get()
    {
        return value;
    }
}

class Pair<K, V>
{
    private K key;
    private V value;

    public Pair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public K getKey()
    {
        return key;
    }

    public V getValue()
    {
        return value;
    }
}

class GenericDemo
{
    public static <T> T getFirst(T[] array)
    {
        if (array == null || array.length == 0)
        {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args)
    {
        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Generics");
        String text = stringBox.get();
        System.out.println("Box content: " + text);

        Pair<String, Integer> pair = new Pair<>("Age", 20);
        System.out.println("Pair: " + pair.getKey() + " = " + pair.getValue());

        Integer[] numbers = {10, 20, 30};
        Integer firstNum = getFirst(numbers);
        System.out.println("First number: " + firstNum);

        String[] words = {"Apple", "Banana", "Cherry"};
        String firstWord = getFirst(words);
        System.out.println("First word: " + firstWord);
    }
}