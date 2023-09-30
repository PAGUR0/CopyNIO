package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            FileCopyExample("copy1.txt", "paste1.txt");
        });

        Thread thread2 = new Thread(() -> {
            FileCopyExample("copy2.txt", "paste2.txt");
        });

        long startTime = System.currentTimeMillis();

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("paste1.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.close();

            file = new File("paste2.txt");
            fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        long endTime = System.currentTimeMillis();
        System.out.println("Синхронные потоки: " + (endTime - startTime));

        startTime = System.currentTimeMillis();

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        endTime = System.currentTimeMillis();
        System.out.println("Асинхронные потоки: " + (endTime - startTime));

    }

    public static void FileCopyExample (String input, String output) {
        Path sourcePath = Paths.get(input);
        Path targetPath = Paths.get(output);

        try {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл успешно скопирован!");
        }
        catch (IOException e) {
            System.err.println("Произошла ошибка при копировании файла: " + e.getMessage());
        }
    }
}

