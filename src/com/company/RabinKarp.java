package com.company;

import java.util.LinkedList;
import java.util.List;

public class RabinKarp {

    public static int p = 11; //простое число
    public static int r = 13;  //остаток получаем от деления на это число

    public static void main(String[] args) {
        String a = "Программирование";
        String b = "мир";
        String t = "testcctectestftef";
        String subt = "te";
        System.out.println("-------");
        for (int i = 0; i < t.length(); i++) {
            System.out.println(i+" "+t.charAt(i));
        }
        System.out.println("-------");
        String t2 = "abcdefghjkdeflmnopq";
        String subt2 = "def";
        System.out.println("======ОТВЕТ======");
        System.out.println(RabinKarp(t, subt));
        System.out.println("===ОТВЕТЫ====");
        System.out.println(RabinKarp(t2, subt2));
    }

    public static int Hash(String x)
    {
        int result = 0;
        int m = x.length();
        for (int i = 0; i < x.length(); i++)
        {
            result += (int) Math.pow(p, m - 1 - i) * (int)(x.charAt(i));
        }
        return result;
    }

    public static List<Integer> RabinKarp(String str, String sub){
        int n = str.length();
        int m = sub.length();
        List<Integer> answers = new LinkedList<>();

        int hashS = Hash(str.substring(0, m)) % r;
        int hashW = Hash(sub) % r;

        System.out.println("---ПЕРВЫЕ ХЭШИ---");
        System.out.println(hashS % r + " hashS");
        System.out.println(hashW % r + " hashW");
        System.out.println("-----------------");

        for(int i = 0; i<n-m; i++){
            if(hashS == hashW){
                answers.add(i);
            }
            hashS = ((hashS - ((int)str.charAt(i) * (int)Math.pow(p, m-1)) % r) * p % r + (int)str.charAt(i+m)) % r;
        }
        if(answers.isEmpty()){
            answers.add(-1);
        }
        return answers;
    }

    static int R(String s, String t) {
        boolean ok = false;
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        for (int i = 0; i <= s1.length - t1.length; i++) {
            ok = true;
            for (int j = 0; j <= t1.length - 1; j++) {
                if (s1[i + j] != t1[j]) {
                    ok = false;
                    break;
                }
            }
            if (ok)
                return i;
        }
        return -1;
    }
}
