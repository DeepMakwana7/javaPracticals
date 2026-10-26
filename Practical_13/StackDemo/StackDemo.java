class Stack<T>
{
    private Object[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    Stack(int capacity)
    {
        elements = new Object[capacity];
        size = 0;
    }

    public void push(T item)
    {
        if (size == elements.length)
        {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
        }
        elements[size++] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop()
    {
        if (size == 0)
        {
            throw new IllegalStateException("Stack is empty");
        }
        T item = (T) elements[--size];
        elements[size] = null;
        return item;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }
}

class StackDemo
{
    public static void main(String[] args)
    {
        Stack<String> stringStack = new Stack<>(2);
        stringStack.push("Java");
        stringStack.push("Generics");

        System.out.println("String Stack:");
        while (!stringStack.isEmpty())
        {
            System.out.println(stringStack.pop());
        }

        Stack<Integer> intStack = new Stack<>(2);
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);

        System.out.println("\nInteger Stack:");
        while (!intStack.isEmpty())
        {
            System.out.println(intStack.pop());
        }
    }
}