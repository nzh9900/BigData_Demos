package org.ni;

public class StringSplitTest {
    public static void main(String[] args) {
        String[] splitTextBySymbol = splitTextBySymbol("\\|\\^\\|", "oo|^|pp|^|uu");
        for (String s : splitTextBySymbol) {
            System.out.println(s);
        }
    }

    public static String[] splitTextBySymbol(String symbol, String text) {
       return text.split(symbol);
    }

}
