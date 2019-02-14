package com.company;

import Jama.Matrix;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        fileReader a = new fileReader();
        String[] l = a.main();
        int len = a.count();
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
        }
        try(FileWriter writer = new FileWriter("C:\\Users\\pc\\Desktop\\1 КУРС\\ICT\\zapis.txt", false))
        {
            for(int i = 1; i< open.length; i++){

                writer.write(String.valueOf(open[i]));
                writer.append('\t');
                writer.append(Double.toString(close[i]));
                writer.append('\n');
            }

            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
