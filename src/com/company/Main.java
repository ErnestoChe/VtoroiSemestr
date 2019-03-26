package com.company;

import Jama.Matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        fileReader file = new fileReader();
        double[] close = file.getClose();
        zapis(close);
        double[][] ans = new double[10][4];
        double[] r = new double[10];
        try {
            ans[0] = solve(file, 7);
            r[0] = R(factori(file, 7), closi(file, 7), ans[0]);

            ans[1] = solve(file, 14);
            r[1] = R(factori(file, 14), closi(file, 14), ans[1]);

            ans[2] = solve(file, 31);
            r[2] = R(factori(file, 31), closi(file, 31), ans[2]);

            ans[3] = solve(file, 60);
            r[3] = R(factori(file, 60), closi(file, 60), ans[3]);

            ans[4] = solve(file, 90);
            r[4] = R(factori(file, 90), closi(file, 90), ans[4]);

            ans[5] = solve(file, 180);
            r[5] = R(factori(file, 180), closi(file, 180), ans[5]);

            ans[6] = solve(file, 365);
            r[6] = R(factori(file, 365), closi(file, 365), ans[6]);

            ans[7] = solve(file, 730);
            r[7] = R(factori(file, 730), closi(file, 730), ans[7]);

            ans[8] = solve(file, 1095);
            r[8] = R(factori(file, 1095), closi(file, 1095), ans[8]);

            ans[9] = solve(factori(file), closi(file));    //Cn = b0 + b1*On + b2*Hn + b3*Ln
            r[9] = R(factori(file), closi(file), ans[9]);

        } catch (ValueException e) {
            e.printStackTrace();
        } catch (СoefficientException e) {
            e.printStackTrace();
        }
        Tree tree = new Tree();
        tree.insert(5, r[5]);
        tree.insert(3, r[3]);
        tree.insert(7, r[7]);
        for (int i = 0; i < 10; i++) {
            if(i != 5 & i != 7 & i != 3)
            tree.insert(i, r[i]);
        }
        tree.print(tree.find(5));
    }

    //записываем в файл
    public static void zapis(double[] close) {
        try (FileWriter writer = new FileWriter("C:\\Users\\pc\\Desktop\\1 КУРС\\ICT\\zapis.txt", false)) {
            for (int i = 1; i < close.length; i++) {
                writer.write(String.valueOf(close[i]));
                writer.append('\t');
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //вычисление коэффициентов по метожу наименьших квадратов
    public double[] mnk(fileReader file) {
        int len = file.main();
        double t = 0;           //t
        double t2 = 0;       //t^2
        double y = 0;       //y
        double ty = 0;     //ty
        double a, b;

        double[] close = file.getClose();
        for (int i = 1; i < len; i++) {
            t += i / len;
            y += close[i] / len;
            ty += i * close[i] / len;
            t2 += Math.pow(i, 2) / len;
        }
        b = (ty - t * y) / (t2 - t * t);
        a = y - b * t;
        System.out.println(a);
        System.out.println(b);
        double[] pred = new double[len];
        for (int i = 1; i < len; i++) {
            pred[i] = a + b * i;
        }
        return pred;

    }

    //первая модель, данные за все время  Cn = b0 + b1*On + b2*Hn + b3*Ln
    public static double[][] factori(fileReader file) throws ValueException {
        int k = file.main();
        double[] open = file.getOpen();
        double[] low = file.getLow();
        double[] high = file.getHigh();
        double[][] x = new double[4][k];
        try {
            for (int i = 0; i < k; i++) {
                x[0][i] = 1;
                x[1][i] = open[i];
                if (open[i] < 1) throw new ValueException("close value less than 1", open[i]);
                x[2][i] = high[i];
                if (high[i] < 1) throw new ValueException("close value less than 1", high[i]);
                x[3][i] = low[i];
                if (low[i] < 1) throw new ValueException("close value less than 1", low[i]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("array out of bounds " + e.getMessage());
        }
        return x;
    }

    public static double[] closi(fileReader file) throws ValueException {
        int k = file.main();
        double[] close = file.getClose();
        double[] y = new double[k];
        for (int i = 0; i < k; i++) {
            y[i] = close[i];
            if (close[i] < 1) throw new ValueException("close value less than 1", close[i]);
        }
        return y;
    }

    //вторая модель, данные за последние 'period' дней
    public static double[][] factori(fileReader file, int period) throws ValueException {
        int k = file.main();
        double[] open = file.getOpen();
        double[] low = file.getLow();
        double[] high = file.getHigh();
        double[][] x = new double[4][period];
        try {
            for (int i = 0; i < period; i++) {
                x[0][i] = 1;
                x[1][i] = open[i + k - period];
                if (open[i] < 1) throw new ValueException("close value less than 1", open[i]);
                x[2][i] = high[i + k - period];
                if (high[i] < 1) throw new ValueException("close value less than 1", high[i]);
                x[3][i] = low[i + k - period];
                if (low[i] < 1) throw new ValueException("close value less than 1", low[i]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("array out of bounds " + e.getMessage());
        }
        return x;
    }

    public static double[] closi(fileReader file, int period) throws ValueException {
        int k = file.main();
        double[] close = file.getClose();
        double[] y = new double[period];
        for (int i = 0; i < period; i++) {
            y[i] = close[i + k - period];
            if (close[i] < 1) throw new ValueException("close value less than 1", close[i]);
        }
        return y;
    }

    //находим коэффициенты за все время
    public static double[] solve(double[][] ff, double[] yy) throws СoefficientException {

        Matrix X = new Matrix(ff);
        Matrix Xt = X.transpose();
        Matrix X1 = X.times(Xt);
        Matrix Xinv = X1.inverse();
        Matrix Y = new Matrix(yy, 1);
        Matrix Xt2 = Xt.times(Xinv);
        Matrix B = Y.times(Xt2);

        double[] z = B.getColumnPackedCopy();
        System.out.println("Используются данные за все время (" + yy.length + " дней)");
        System.out.println("Коэффициенты:");
        for (int i = 0; i < 4; i++) {
            if (z[i] == 0) throw new СoefficientException("factor equals zero", z[i]);
            System.out.println("B" + i + "=" + z[i]);
        }
        return z;
    }

    //нахождения коэффициентов за последние 'period' дней
    public static double[] solve(fileReader file, int period) throws СoefficientException {

        Matrix X = null;
        try {
            X = new Matrix(factori(file, period));
        } catch (ValueException e) {
            e.printStackTrace();
        }
        Matrix Xt = X.transpose();
        Matrix X1 = X.times(Xt);
        Matrix Xinv = X1.inverse();
        Matrix Y = null;
        try {
            Y = new Matrix(closi(file, period), 1);
        } catch (ValueException e) {
            e.printStackTrace();
        }
        Matrix Xt2 = Xt.times(Xinv);
        Matrix B = Y.times(Xt2);

        double[] z = B.getColumnPackedCopy();
        System.out.println("Испольщуются данные за " + period + " дней");
        System.out.println("Коэффициенты:");
        for (int i = 0; i < 4; i++) {
            if (z[i] == 0) throw new СoefficientException("factor equals zero", z[i]);
            System.out.println("B" + i + "=" + z[i]);
        }
        //System.out.println();
        return z;
    }

    //логирование матриц для удобства
    public static void log(String name, Matrix M) {
        System.out.println(name);
        System.out.println(M.getRowDimension() + " x " + M.getColumnDimension());
        System.out.println();
    }

    //коэффициент Детерминации
    public static double R(double[][] ff, double[] yy, double[] z) {
        double r = 0, S1 = 0, S2 = 0, S3 = 0;
        double[] u1 = new double[yy.length];
        double[] u2 = new double[yy.length];
        for (int i = 0; i < yy.length; i++) {
            S3 += yy[i];
            u1[i] = 0;
        }
        S3 = S3 / 5;
        for (int m = 0; m < 5; m++) {
            u1[m] = (z[0] + z[1] * ff[1][m] + z[2] * ff[2][m] - yy[m]) * (z[0] + z[1] * ff[1][m] + z[2] * ff[2][m] - yy[m]);
            S1 += u1[m];
            u2[m] = (S3 - yy[m]) * (S3 - yy[m]);
            S2 += u2[m];
        }
        r = 1 - S1 / S2;
        System.out.println("r=" + r);
        System.out.println();
        return r;
    }
}

class ValueException extends Exception {
    double value;

    public double getValue() {
        return value;
    }

    public ValueException(String msg, double v) {
        super(msg);
        value = v;
    }
}

class СoefficientException extends Exception {
    double koef;

    public double getK() {
        return koef;
    }

    public СoefficientException(String msg, double v) {
        super(msg);
        koef = v;
    }
}