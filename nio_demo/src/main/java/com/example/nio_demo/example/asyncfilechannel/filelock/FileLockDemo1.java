package com.example.nio_demo.example.asyncfilechannel.filelock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @ClassName FileLockDemo1
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-22 22:54
 * @Version 1.0
 */
public class FileLockDemo1 {
    public static void main(String[] args) throws IOException {
        String input = "nonbioclock";
        System.out.println("input: " + input);

        ByteBuffer buffer = ByteBuffer.wrap(input.getBytes());
        String filePath = "C:\\Users\\86137\\Desktop\\test1\\fileLockDemo1.txt";
        Path path = Paths.get(filePath);
        //追加写的模式
        FileChannel channel = FileChannel.open(path,
                StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        //加锁
        FileLock lock = channel.lock();
        System.out.println("是否是共享锁： " + lock.isShared());
        channel.write(buffer);
        channel.close();
        //读文件
        readFile(filePath);
    }

    private static void readFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader);
        String tr = br.readLine();
        System.out.println("读取出内容：");
        while(tr != null){
            System.out.println(" " + tr);
            tr = br.readLine();
        }
        fileReader.close();
        br.close();
    }

}
