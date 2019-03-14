package com.company;

import Jama.Matrix;
import com.sun.jdi.Value;

import java.io.FileWriter;
import java.io.IOException;
import java.util.spi.AbstractResourceBundleProvider;

public class Main {
    public static void main(String[] args) {
        fileReader file = new fileReader();
        double[] close = file.getClose();
        double[] open = file.getOpen();
        double[] high = file.getHigh();
        double[] low = file.getLow();
        zapis(close);

        try {
            double[] otveti = solve(factori(file), closi(file));    //Cn = b0 + b1*On + b2*Hn + b3*Ln
            double r = R(factori(file), closi(file), otveti);
            double[] ans_week = solve(file, 7);
            double[] ans_month = solve(file , 31);
            double[] ans_half_year = solve(file, 180);
            double[] ans_year = solve(file, 365);
            double[] ans_2years = solve(file, 730);
        } catch (ValueException e) {
            e.printStackTrace();
        } catch (СoefficientException e) {
            e.printStackTrace();
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

        double[]close = file.getClose();
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

    //первая модель, данные за все время  Cn = b0 + b1*On + b2*Hn + b3*Ln
    public static double[][] factori(fileReader file) throws ValueException{
        int k = file.main();
        double[] open = file.getOpen();
        double[] low = file.getLow();
        double[] high = file.getHigh();
        double[][] x = new double[4][k];
        try{
            for(int i =0; i<k; i++){
                x[0][i] = 1;
                x[1][i] = open[i];
                if(open[i] < 1) throw new ValueException("close value less than 1", open[i]);
                x[2][i] = high[i];
                if(high[i] < 1) throw new ValueException("close value less than 1", high[i]);
                x[3][i] = low[i];
                if(low[i] < 1) throw new ValueException("close value less than 1", low[i]);
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("array out of bounds "+e.getMessage());
        }
        return x;
    }
    public static double[] closi(fileReader file)throws ValueException{
        int k = file.main();
        double[] close = file.getClose();
        double[] y = new double[k];
        for(int i =0; i<k; i++){
            y[i] = close[i];
            if(close[i] < 1) throw new ValueException("close value less than 1", close[i]);
        }
        return y;
    }

    //вторая модель, данные за последний месяц
    public static double[][] factori_period(fileReader file, int period) throws ValueException{
        int k = file.main();
        double[] open = file.getOpen();
        double[] low = file.getLow();
        double[] high = file.getHigh();
        double[][] x = new double[4][period];
        try{
            for(int i = 0; i < period; i++){
                x[0][i] = 1;
                x[1][i] = open[i + k - period];
                if(open[i] < 1) throw new ValueException("close value less than 1", open[i]);
                x[2][i] = high[i + k - period];
                if(high[i] < 1) throw new ValueException("close value less than 1", high[i]);
                x[3][i] = low[i + k - period];
                if(low[i] < 1) throw new ValueException("close value less than 1", low[i]);
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("array out of bounds "+e.getMessage());
        }
        return x;
    }
    public static double[] closi_period(fileReader file, int period)throws ValueException{
        int k = file.main();
        double[] close = file.getClose();
        double[] y = new double[period];
        for(int i = 0; i < period; i++){
            y[i] = close[i + k - period];
            if(close[i] < 1) throw new ValueException("close value less than 1", close[i]);
        }
        return y;
    }


    public static double[] solve(double[][]ff, double[]yy) throws СoefficientException{

        Matrix X = new Matrix(ff);
        Matrix Xt = X.transpose();
        Matrix X1 = X.times(Xt);
        Matrix Xinv = X1.inverse();
        Matrix Y = new Matrix(yy, 1);
        Matrix Xt2 = Xt.times(Xinv);
        Matrix B = Y.times(Xt2);

        double[] z = B.getColumnPackedCopy();
        System.out.println("Используются данные за все время " + yy.length);
        System.out.println("Коэффициенты:");
        for (int i = 0; i < 4; i++){
            if(z[i] == 0) throw new СoefficientException("factor equals zero" , z[i]);
            System.out.println("B"+i+"="+z[i]);
        }
        System.out.println();
        return z;
    }
    public static double[] solve(fileReader file, int period) throws СoefficientException{

        Matrix X = null;
        try {
            X = new Matrix(factori_period(file, period));
        } catch (ValueException e) {
            e.printStackTrace();
        }
        Matrix Xt = X.transpose();
        Matrix X1 = X.times(Xt);
        Matrix Xinv = X1.inverse();
        Matrix Y = null;
        try {
            Y = new Matrix(closi_period(file, period), 1);
        } catch (ValueException e) {
            e.printStackTrace();
        }
        Matrix Xt2 = Xt.times(Xinv);
        Matrix B = Y.times(Xt2);

        double[] z = B.getColumnPackedCopy();
        System.out.println("Испольщуются данные за " + period + " дней");
        System.out.println("Коэффициенты:");
        for (int i = 0; i < 4; i++){
            if(z[i] == 0) throw new СoefficientException("factor equals zero" , z[i]);
            System.out.println("B"+i+"="+z[i]);
        }
        System.out.println();
        return z;
    }
    public static void log(String name, Matrix M){
        System.out.println(name);
        System.out.println(M.getRowDimension() + " x " + M.getColumnDimension());
        System.out.println();
    }

    //коэффициент Детерминации
    public static double R(double[][] ff, double[] yy, double[] z){
        return 0;
    }
}
class ValueException extends Exception{
    double value;

    public double getValue() {
        return value;
    }
    public ValueException(String msg, double v){
        super(msg);
        value = v;
    }
}

class СoefficientException extends Exception{
    double koef;

    public double getK() {
        return koef;
    }
    public СoefficientException(String msg, double v){
        super(msg);
        koef = v;
    }
}