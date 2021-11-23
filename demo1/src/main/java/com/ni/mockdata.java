package com.ni;

public class mockdata {
    public static void main(String[] args) {
        StringBuffer in = new StringBuffer();
        for (int i = 0; i < 10000; i++) {
            in.append("R75Y/wEyedAEQRP0no2tUA==").append(",");
        }
        System.out.println(in);
    }
}
