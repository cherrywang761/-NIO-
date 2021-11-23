package com.example.nio_demo.example.asyncfilechannel.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @ClassName FilesDemo
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-22 23:29
 * @Version 1.0
 */
public class FilesDemo {
    //演示Files类中几个常用方法
    public static void main(String[] args) {
        //walkFileTree()
        //去rootPath递归找fileToFind文件
        Path rootPath = Paths.get("C:\\Users\\86137\\Desktop");
        String fileToFind = File.separator + "hello.txt";

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();

                    if (fileString.endsWith(fileToFind)) {
                        System.out.println("file found at path: " + file.toAbsolutePath());
                        //终止操作
                        return FileVisitResult.TERMINATE;
                    }
                    //继续操作
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
