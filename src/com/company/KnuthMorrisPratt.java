package com.company;

import java.util.Vector;

public class KnuthMorrisPratt {
    public static void main(String[] args) {
        String test = "wrong123 wrong 456 test wrongwrong string, what wrong could possibly go wrong";
        for (int i = 0; i < test.length(); i++) {
            System.out.println(i + " " + test.charAt(i));
        }
        String sub_test = "g";
        System.out.println(ans(sub_test, test));
    }

    public static int[] prefix(String str){
        int p[] = new int[str.length()];
        p[0] = 0;
        int k = 0;
        char[] s = str.toCharArray();
        for (int i = 1; i < str.length(); i++) {
            k = p[i-1];
            while(k > 0 && s[i] != s[k])
                k = p[k - 1];
            if(s[i] == s[k]) k++;
            p[i] = k;
        }
        return p;
    }

    public static Vector<Integer> ans(String substr, String str){
        Vector<Integer> an = new Vector<>();
        if(substr.length() > str.length()){
            an.add(-1);
        }else{
            int tl = str.length();
            int pl = substr.length();
            String full_str = substr + "" + str;
            int[] p = prefix(full_str);
            for (int i = 0; i < tl; i++) {
                if(p[pl+i] == pl){
                    an.add(i - substr.length() + 1);
                }
            }
            if(an.isEmpty()){
                an.add(-1);
            }
        }
        return an;
    }
}
