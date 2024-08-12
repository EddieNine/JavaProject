package EstruturasDeDados.Mapas;

import java.util.HashMap;
import java.util.Map;

public class MapExample {
    public static void main(String[] args) {
        Map<String, String> phoneBook = new HashMap<>();

        phoneBook.put("Alice", "123-456-7890");
        phoneBook.put("Bob", "987-654-3210");

        System.out.println("Alice's phone number: " + phoneBook.get("Alice"));

        for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
