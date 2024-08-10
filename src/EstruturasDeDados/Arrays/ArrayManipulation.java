package EstruturasDeDados.Arrays;

import java.util.Arrays;

public class ArrayManipulation {
    public static void main(String[] args) {
        int[] originalArray = {1, 2, 3, 4, 5};

        // Copiar array
        int[] copiedArray = Arrays.copyOf(originalArray, originalArray.length);

        // Preencher array
        Arrays.fill(copiedArray, 5);

        // Exibir arrays
        System.out.println("Original Array: " + Arrays.toString(originalArray));
        System.out.println("Copied Array: " + Arrays.toString(copiedArray));
    }
}
