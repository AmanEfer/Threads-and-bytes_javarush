package com.javarush.task.task18.task1823;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/* 
Нити и байты
*/

public class Solution {
    public static Map<String, Integer> resultMap = new HashMap<String, Integer>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String fileName = reader.readLine();
            if (fileName.equalsIgnoreCase("exit"))
                break;

            ReadThread thread = new ReadThread(fileName);
            thread.start();
        }
//      C:\\Users\\afilt\\Desktop\\1.txt
//        Thread.sleep(3000);
//        for (Map.Entry<String, Integer> pair : resultMap.entrySet()) {
//            System.out.println(pair.getKey() + " = " + (char)pair.getValue().intValue());
    }


    public static class ReadThread extends Thread {
        private String fileName;
        public ReadThread(String fileName) {
            //implement constructor body
            this.fileName = fileName;
        }
        // implement file reading here - реализуйте чтение из файла тут
        @Override
        public void run() {
            try (FileInputStream fis = new FileInputStream(fileName)) {

                ArrayList<Integer> arrayList = new ArrayList<>();

                while (fis.available() > 0) {
                    arrayList.add(fis.read());
                }

                HashSet<Integer> hashSet = new HashSet<>(arrayList);
                HashMap<Integer, Integer> hashMap = new HashMap<>();

                for (Integer key : hashSet) {
                    int value = Collections.frequency(arrayList, key);
                    hashMap.put(key, value);
                }

                int max = Collections.max(hashMap.values());
                ArrayList<Integer> maxElementsList = new ArrayList<>();

                for (Map.Entry<Integer, Integer> pair : hashMap.entrySet()) {
                    int key = pair.getKey();
                    int value = pair.getValue();
                    if (value == max)
                        maxElementsList.add(key);
                }

                int result = Integer.MAX_VALUE;

                for (Integer current : maxElementsList) {
                    result = Math.min(result, current);
                }

                synchronized (resultMap) {
                    resultMap.put(fileName, result);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
