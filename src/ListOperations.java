import java.util.ArrayList;
import java.util.List;

public class ListOperations {
    public static void main(String[] args) {
        List<String> tasks = new ArrayList<>();

        tasks.add("Complete homework");
        tasks.add("Read a book");
        tasks.add("Go for a walk");

        // Remover elemento
        tasks.remove("Read a book");

        // Iterar sobre a lista
        for (String task : tasks) {
            System.out.println(task);
        }
    }
}
