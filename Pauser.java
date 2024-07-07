public class Pauser {

    // Method to pause for a specified number of milliseconds
    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Sleep interrupted");
        }
    }

    // Method to print a message with a delay between characters
    public static void printWithDelay(String message, int delay) {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            pause(delay);
        }
        System.out.println();
    }
}