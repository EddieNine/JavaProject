import java.util.HashSet;
import java.util.Set;

public class SetOperations {
    public static void main(String[] args) {
        Set<String> setA = new HashSet<>();
        Set<String> setB = new HashSet<>();

        setA.add("A");
        setA.add("B");
        setA.add("B");
        setA.add("C");

        // União
        Set<String> union = new HashSet<>(setA);
        union.addAll(setB);

        // Iterseção
        Set<String> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);

        // Diferença
        Set<String> difference = new HashSet<>(setA);
        difference.removeAll(setB);

        System.out.println("Union: " + union);
        System.out.println("Intersection: " + intersection);
        System.out.println("Difference: " + difference);
    }
}
