package com.company;

import java.io.*;

public class fileReader {
    String[] lines = new String[40];
    int i = 0;

    public String[] main() {
        try {
            File file = new File("C:\\Users\\pc\\Desktop\\1 КУРС\\ICT\\EURUSD_190101_190201.txt");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
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
        return lines;
    }
    public int count(){
        return i;
    }
}
