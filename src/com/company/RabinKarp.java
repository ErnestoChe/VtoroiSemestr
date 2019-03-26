package com.company;

import java.util.LinkedList;
import java.util.List;

public class RabinKarp {
    public static void main(String[] args) {
        String a = "Программирование";
        String b = "мир";
        String test = "aaa test bbb test ccc test ff";
        String subtest = "test";
        int r = R(a, b);
        List ans = RabinKarp(a,b);
        List ans_test = RabinKarp(test, subtest);
        int r_test = R(test, subtest);
        //System.out.println(ans);
        System.out.println(ans_test);
        System.out.println(r_test);
    }

    public static int Hash(String x)
    {
        int p = 31; //Простое число
        int result = 0;
        //считаем хэш строки
        for (int i = 0; i < x.length(); i++)
        {
            result += (int)Math.pow(p,x.length()-1-i)*(int)(x.charAt(i));
        }
        return result;
    }

    public static List<Integer> RabinKarp(String str, String sub){
        int n = str.length();
        int m = sub.length();
        List<Integer> answers = new LinkedList<>();
        int p = 53;
        int x = 3;
        int r = 51;
        int sub_l = sub.length();
        int hashS = Hash(str.substring(0, sub_l));
        int hashW = Hash(sub);

        for(int i = 0; i<n-m; i++){
            if(hashS == hashW){
                answers.add(i-1);
            }
            //TODO сокразенная формула перерасчета хэша
            //hashS = (p * hashS - (int)Math.pow(p, m) * Hash(str.substring(i,i+1)) + Hash(str.substring(i+m, i+m+1))) % r;
            hashS = Hash(str.substring(i, i+sub_l));
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
