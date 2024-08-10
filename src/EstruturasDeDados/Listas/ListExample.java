package EstruturasDeDados.Listas;

import java.util.ArrayList;
import java.util.List;

public class ListExample {
    public static void main(String[] args) {
        // Criar uma lista
        List<String> tasks = new ArrayList<>();

        // Adicionar elementos
        tasks.add("Complete homework");
        tasks.add("Read a book");

        // Acessar elementos
        System.out.println("First task: " + tasks.get(1));

        // Iterar sobre a lista
        for (String task : tasks) {
            System.out.println(task);
        }
    }
}
