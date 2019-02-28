package com.company;

import java.io.*;

public class fileReader {
    String[] lines = new String[1500];

    public int main() {
        int i = 0;
        try {
            //File file = new File("C:\\Users\\pc\\Desktop\\1 КУРС\\ICT\\EURUSD_190101_190201.txt");
            //File file = new File("C:\\Users\\pc\\Desktop\\1 КУРС\\ICT\\EURUSD_180216_190216.txt");
            File file = new File("C:\\Users\\pc\\Desktop\\1 КУРС\\ICT\\EURUSD_150216_190216.txt");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();

            while (line != null) {
                //System.out.println(line);
                lines[i] = line;
                i++;
                // считываем остальные строки в цикле
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;     //последняя строка - формат данных в .txt файле     <TICKER>,<PER>,<DATE>,<TIME>,<OPEN>,<HIGH>,<LOW>,<CLOSE>,<VOL>
        //return lines;
    }
    public double[] getClose(){
        int j = main();
        double[] close = new double[j];
        for(int k = 0; k<j; k++){
            close[k] = Double.parseDouble(lines[k].substring(55,64));
        }
        return close;
    }
    public double[] getOpen(){
        int j = main();
        double[] open = new double[j];
        for(int k = 0; k<j; k++){
            open[k] = Double.parseDouble(lines[k].substring(25,34));
        }
        return open;
    }
    public double[] getHigh(){
        int j = main();
        double[] high = new double[j];
        for(int k = 0; k<j; k++){
            high[k] = Double.parseDouble(lines[k].substring(35,44));
        }
        return high;
    }
    public double[] getLow(){       //"э рон дон дон" starts playing
        int j = main();
        double[] low = new double[j];
        for(int k = 0; k<j; k++){
            low[k] = Double.parseDouble(lines[k].substring(45,54));
        }
        return low;
    }
}
