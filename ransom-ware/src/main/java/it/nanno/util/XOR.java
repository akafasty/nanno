package it.nanno.util;

public class XOR {

    public static String xor(final String string, final int xor) {
        final char[] array = new char[string.length()];
        for (int i = 0; i < string.length(); i++) {
            array[i] = (char) (string.charAt(i) ^ xor);
        }
        return String.valueOf(array);
    }

    public static String dexor(final String string, final int xor) {

        final char[] array = new char[string.length()];

        for (int i = 0; i < string.length(); i++) {
            array[i] = (char) (string.charAt(i) ^ xor);
        }

        return new String(array);
    }

}
