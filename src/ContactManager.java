import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ContactManager {
    public static void main(String[] args) {
        Set<String> contacts = new HashSet<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a contact (or type 'exit' to quit): ");
            String contact = scanner.nextLine();

            if (contact.equalsIgnoreCase("exit")) {
                break;
            }

            contacts.add(contact);
        }

        System.out.println("Your contacts:");
        for (String c : contacts) {
            System.out.println("- " + c);
        }

        scanner.close();
    }
}
