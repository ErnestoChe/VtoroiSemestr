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
        int len = file.main();
        double t = 0;           //t
        double t2 = 0;       //t^2
        double y = 0;       //y
        double ty = 0;     //ty
        double a, b;
        for(int i = 1; i<len; i++){
            t += i/len;
            y += close[i]/len;
            ty += i * close[i]/len;
            t2 += Math.pow(i,2)/len;
        }

        b = (ty - t * y)/(t2 - t*t);
        a = y - b * t;

        System.out.println(a);
        System.out.println(b);


        double[] pred = new double[len];
        for(int i = 1; i<len; i++){
            pred[i] = a + b * i;
            //System.out.println(i+ "\t" + (close[i]) + "\t" + pred[i]);
        }
        try(FileWriter writer = new FileWriter("C:\\Users\\pc\\Desktop\\1 КУРС\\ICT\\zapis.txt", false))
        {
            for(int i = 1; i< close.length; i++){

                writer.write(String.valueOf(close[i]));
                writer.append('\t');
                writer.append('\n');
            }

            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        //test(factori(file), closi(file));
        Matrix m = new Matrix(factori(file));
        System.out.println(m.getColumnDimension());
        System.out.println(m.getRowDimension());

        Matrix m1 = new Matrix(closi(file), 1);
        System.out.println(m1.getColumnDimension());
        System.out.println(m1.getRowDimension());
        m1.print(5, 5);
        m.print(5,5);
    }
    public static double[][] factori(fileReader file){
        int k = file.main();
        double[] open = file.getOpen();
        double[] close = file.getClose();
        double[] low = file.getLow();
        double[] high = file.getHigh();
        double[][] x = new double[4][k];
        for(int i =0; i<k; i++){
            x[0][i] = 1;
            x[1][i] = open[i];
            x[2][i] = high[i];
            x[3][i] = low[i];
        }
        return x;
    }
    public static double[] closi(fileReader file){
        int k = file.main();
        double[] close = file.getClose();
        double[] y = new double[k];
        for(int i =0; i<k; i++){
            y[i] = close[i];
        }
        return y;
    }
    public static void test(double[][]ff, double[]yy){
        Matrix A1=new Matrix(ff);
        double[] z = new double[5];
        //A1.print(10, 2);
        Matrix B1=A1.transpose();
        Matrix F1=A1.times(B1);
        Matrix F4=F1.inverse();
        Matrix F2=F4.times(A1);
        Matrix C=new Matrix(yy,4);
        Matrix F3=F2.times(C);
        z=F3.getColumnPackedCopy();
        System.out.println("Коэффициенты:");
        for (int i = 0; i < 4; i++)
            System.out.println("B"+i+"="+z[i]);

    }
}
