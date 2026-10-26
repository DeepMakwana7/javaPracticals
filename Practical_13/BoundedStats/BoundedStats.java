class BoundedStats
{
    public static <N extends Number> double average(N[] values)
    {
        if (values == null || values.length == 0)
        {
            return 0.0;
        }

        double sum = 0.0;
        for (N value : values)
        {
            sum += value.doubleValue();
        }

        return sum / values.length;
    }

    public static void main(String[] args)
    {
        Integer[] integers = {1, 2, 3, 4, 5};
        Double[] doubles = {1.5, 2.5, 3.5};
        Long[] longs = {10L, 20L, 30L};

        System.out.println("Integer average: " + average(integers));
        System.out.println("Double average: " + average(doubles));
        System.out.println("Long average: " + average(longs));
    }
}