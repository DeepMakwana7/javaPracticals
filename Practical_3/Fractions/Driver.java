public class Driver {
    public static void main(String[] args) {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);

        System.out.println("new Fraction(1, 2) prints: " + f1);
        System.out.println("new Fraction(2, 4) prints: " + f2);
        System.out.println("new Fraction(3, 6) prints: " + f3);


        System.out.println("\nAre f1 and f2 equal? " + f1.equals(f2));
        System.out.println("Are f2 and f3 equal? " + f2.equals(f3));
        System.out.println("Are f1 and f3 equal? " + f1.equals(f3));
    }
}