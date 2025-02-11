import java.util.ArrayList;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class ArrayListQueue 
{
    static ArrayList<String> queue = new ArrayList<String>();
    static Scanner input = new Scanner(System.in);
    static int servedCount = 0;

    public static void main(String[] args)
    {
        Date myDate = new Date();
        String myDateFormat = "MM/dd/yyyy";
        SimpleDateFormat dtToday = new SimpleDateFormat(myDateFormat);

        // Populate the list with some initial elements
        queue.add("Mauricio"); 
        queue.add("Juan"); 
        queue.add("Ivan");
        queue.add("Kevin");
        queue.add("Darcy");

        // Initial queue display
        displayQueue(dtToday);

        int choice;
        do {
            choice = menuDisplay();
            switch (choice) {
                case 1 -> enqueue();
                case 2 -> dequeue();
                case 3 -> isEmpty();
                case 4 -> size();
                case 5 -> peek();
                case 6 -> custCount();
                case 7 -> System.out.println("Exiting program.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 7);
    }

    // Display queue elements
    private static void displayQueue(SimpleDateFormat dtToday) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> itr = queue.iterator();
        while (itr.hasNext()) {
            sb.append(itr.next()).append("\n");
        }
        JOptionPane.showMessageDialog(null, dtToday.format(new Date()) + "\nQueue:\n" + sb);
    }

    // Display menu and return user choice
    public static int menuDisplay() {
        System.out.println("Select an operation:\n1. Enqueue\n2. Dequeue\n3. Is Empty\n4. Size\n5. Peek\n6. Customer Count\n7. Exit");
        return input.nextInt();
    }

    // Add a customer to the queue
    public static void enqueue() {
        System.out.println("Enter customer name to add to the queue:");
        input.nextLine();  // Consume newline
        String customer = input.nextLine();
        queue.add(customer);
        System.out.println("Customer added to the queue.");
        displayCurrentQueue();
    }

    // Remove a customer from the front of the queue
    public static void dequeue() {
        if (!queue.isEmpty()) {
            String customer = queue.remove(0);
            servedCount++;
            System.out.println("Customer " + customer + " has been served and removed from the queue.");
        } else {
            System.out.println("Queue is empty. No customers to serve.");
        }
        displayCurrentQueue();
    }

    // Check if the queue is empty
    public static void isEmpty() {
        if (queue.isEmpty()) {
            System.out.println("The queue is empty.");
        } else {
            System.out.println("The queue is not empty.");
        }
        displayCurrentQueue();
    }

    // Display the size of the queue
    public static void size() {
        System.out.println("Current queue size: " + queue.size());
    }

    // Peek at the next customer to be served
    public static void peek() {
        if (!queue.isEmpty()) {
            System.out.println("Next customer in line: " + queue.get(0));
        } else {
            System.out.println("Queue is empty. No customers to peek.");
        }
    }

    // Track the number of customers served
    public static void custCount() {
        System.out.println("Total number of customers served: " + servedCount);
    }

    // Method to display current queue status
    public static void displayCurrentQueue() {
        System.out.println("-------------------------------------------------");
    	System.out.println("Current queue: " + queue);
    	System.out.println("-------------------------------------------------");
    }
}