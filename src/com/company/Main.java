package com.company;

import Jama.Matrix;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        fileReader file = new fileReader();
        double[] close = file.getClose();
        double[] open = file.getOpen();
        double[] high = file.getHigh();
        double[] low = file.getLow();
        zapis(close);

        Matrix m = new Matrix(factori(file));
        Matrix m1 = new Matrix(closi(file), 1);
        Matrix m_t = m.transpose();

        double[] otveti = test(factori(file), closi(file));
        for(int i = 0; i<10; i++){

            double c = otveti[0] + otveti[1] * open[i] + otveti[2] * high[i] + otveti[3] * low[i];
            System.out.println("real close " + close[i]);   //реальная цена
            System.out.format("%.4f%n", c);         //предполагаемая цена
            System.out.println(Math.abs(close[i] - c));     //сравнение исходных и полученных данных
        }
    }

    public static void zapis(double[] close){
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
    }

    public double[] mnk(fileReader file){
        int len = file.main();
        double t = 0;           //t
        double t2 = 0;       //t^2
        double y = 0;       //y
        double ty = 0;     //ty
        double a, b;
        double[] close = file.getClose();
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
        }
        return pred;

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
    public static double[] test(double[][]ff, double[]yy){
        Matrix X = new Matrix(ff);
        log("X", X);
        Matrix Xt = X.transpose();
        log("Xt", Xt);
        Matrix X1 = X.times(Xt);
        log("x1", X1);
        Matrix Xinv = X1.inverse();
        log("Xinv", Xinv);
        Matrix Y = new Matrix(yy, 1);
        log("y", Y);
        Matrix Xt2 = Xt.times(Xinv);
        log("xt2", Xt2);
        Matrix B = Y.times(Xt2);
        log("B", B);

        double[] z = new double[4];
        z = B.getColumnPackedCopy();
        System.out.println("Коэффициенты:");
        for (int i = 0; i < 4; i++){
            System.out.println("B"+i+"="+z[i]);
        }
        return z;
    }
    public static void log(String name, Matrix M){
        System.out.println(name);
        System.out.println(M.getRowDimension() + " x " + M.getColumnDimension());
        System.out.println();
    }
}
