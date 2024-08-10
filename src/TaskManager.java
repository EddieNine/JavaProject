import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        List<String> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a task (or type 'exit' to quit): ");
            String task = scanner.nextLine();

            if(task.equalsIgnoreCase("exit")) {
                break;
            }

            tasks.add(task);
        }

        System.out.println("Your tasks:");
        for (String t : tasks) {
            System.out.println("- " + t);
        }

        scanner.close();
    }
}
