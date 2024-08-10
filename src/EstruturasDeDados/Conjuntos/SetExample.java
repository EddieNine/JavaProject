package EstruturasDeDados.Conjuntos;

import java.util.HashSet;
import java.util.Set;

public class SetExample {
    public static void main(String[] args) {
        Set<String> contacts = new HashSet<>();

        contacts.add("Alice");
        contacts.add("Bob");
        contacts.add("Alice"); // Não será adicionado novamente

        System.out.println("Contacts: " + contacts);
    }
}
