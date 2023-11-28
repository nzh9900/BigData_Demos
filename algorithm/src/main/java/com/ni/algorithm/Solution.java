package com.ni.algorithm;

class Solution {
    public static void main(String[] args) {
        System.out.println(countDigits(7));
    }

    public static int countDigits(int num) {
        int count = 0;
        String numString = String.valueOf(num);
        for(int i=0;i<numString.length();i++){
            int a= (int) numString.charAt(i);
            if(num%a==0){
                count++;
            }
        }
        return count;
    }
}