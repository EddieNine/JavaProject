package EstruturasDeDados.Mapas;

import java.util.HashMap;
import java.util.Map;

public class MapOperations {
    public static void main(String[] args) {
        Map<String, Integer> wordCount = new HashMap<>();
        String text = "hello world hello";
        String[] words = text.split(" ");
        
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        System.out.println("Word count: " + wordCount);
    }
}
