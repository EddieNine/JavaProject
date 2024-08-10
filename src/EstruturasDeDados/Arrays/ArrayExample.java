package EstruturasDeDados.Arrays;

public class ArrayExample {
    public static void main(String[] args) {
        // Declaração e inicialização de um array
        int[] numbers = {1, 2, 3, 4, 5};

        // Acesso e modificação de elementos
        numbers[0] = 10; // Modifica o primeiro elemento

        // // Exibição dos elementos
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Element at index " + i + ": " + numbers[i]);
        }
    }
}
