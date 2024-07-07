public class P {

    // Method for printing without a newline
    public static void p(String message) {
        System.out.print(message);
    }

    // Method for printing with a newline
    public static void pln(String message) {
        System.out.println(message);
    }

    // Method for formatted printing
    public static void pf(String format, Object... args) {
        System.out.printf(format, args);
    }

    // Add more methods as needed, for example, printing arrays, collections, etc.
}
