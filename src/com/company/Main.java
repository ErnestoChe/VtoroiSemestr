package com.company;

import Jama.Matrix;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        fileReader file = new fileReader();
        //String[] l = a.main();
        /*int len = a.getLength();
        int[] date = new int[len];
        double[] open = new double[len];
        double[] close = new double[len];
        double[] high = new double[len];
        double[] low = new double[len];
        for(int i = 1; i<34; i++){
            date[i] = Integer.parseInt(l[i].substring(9,17));
            open[i] = Double.parseDouble(l[i].substring(25,34));
            close[i] = Double.parseDouble(l[i].substring(55,64));
            high[i] = Double.parseDouble(l[i].substring(35,44));
            low[i] = Double.parseDouble(l[i].substring(45,54));
            System.out.println(i + "\t" + date[i] + "\t" + open[i] + "\t" + close[i] + "\t" + high[i] + "\t" + low[i]);
        }*/
        double[] close = file.getClose();
        int len = close.length;
        int l = len - 1;
        double t = 0;           //t
        double t2 = 0;       //t^2
        double y = 0;       //y
        double ty = 0;     //ty
        double a, b;
        /*for(int i = 1; i<len; i++){
            System.out.println(i + "\t" + close[i]);
            t += i; //t
            t2 += Math.pow(i, 2);    //t^2
            y += close[i];  //y
            ty += close[i] * i; //ty
        }
        t /= len;   //t
        t2 /= len;   //t^2
        y /= len;   //y
        ty /= len; //ty

        b = (ty - t * y)/(t2 - t * t);
        a = y - b * t;*/

        y = close[1] + close[2] + close[3];
        t = (1 + 2 + 3) / 3;
        ty = close[1] + 2 * close[2] + 3 * close[3];
        t2 = (1 + 2^2 + 3^2) / 3;

        b = (ty - t * y) / ()


        /*b = (ty * t - y) / (Math.pow(t, 2) - l);
        a = (y - b * l) / t;*/

        System.out.println(a);
        System.out.println(b);

        double[] pred = new double[len];
        for(int i = 1; i<len; i++){
            pred[i] = a + b * (i);
            System.out.println(i+ "\t" + close[i] + "\t" + pred[i]);
        }
        try(FileWriter writer = new FileWriter("C:\\Users\\pc\\Desktop\\1 КУРС\\ICT\\zapis.txt", false))
        {
            for(int i = 1; i< close.length; i++){

                writer.write(String.valueOf(close[i]));
                writer.append('\t');
                //writer.append(Double.toString(close[i]));
                writer.append('\n');
            }

            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
