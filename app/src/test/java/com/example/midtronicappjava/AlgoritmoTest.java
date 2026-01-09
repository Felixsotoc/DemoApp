package com.example.midtronicappjava;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AlgoritmoTest {

    @Test
    public void testCompresion() {
        assertEquals("a3b2c1d3", compressString("aaabbcddd"));
        assertEquals("a1b1c1", compressString("abc"));
    }
    /*La lógica que utilice es comparar la letra actual con la siguiente
    La idea es que si la letra actual es "a" y la siguiente es "a" también entonces
    el count aumenta hasta que es diferente es cuando guarda la 1era letra y
    le concatena el count que va. Así hasta terminar el string */
    public static String compressString(String input) {
        if (input == null || input.isEmpty()) return "";
        input = input.toLowerCase();
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 0; i < input.length(); i++) {
            if (i + 1 < input.length() && input.charAt(i) == input.charAt(i + 1)) {
                count++;
            } else {
                sb.append(input.charAt(i)).append(count);
                count = 1;
            }
        }
        return sb.toString();
    }
}