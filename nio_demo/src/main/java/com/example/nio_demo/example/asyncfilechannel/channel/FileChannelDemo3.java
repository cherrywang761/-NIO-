package com.example.nio_demo.example.asyncfilechannel.channel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @ClassName FileChannelDemo3
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-20 0:24
 * @Version 1.0
 */
public class FileChannelDemo3 {
    //演示通道间数据传输(TransferFrom)
    public static void main(String[] args) throws Exception{
        String parentPath = "C:\\Users\\86137\\Desktop\\test1";
        //创建两个FileChannel
        //from 通道
        RandomAccessFile fromFile = new RandomAccessFile(parentPath + "\\from.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();
        //to 通道
        RandomAccessFile toFile = new RandomAccessFile(parentPath + "\\to.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        //把from通道的数据传输到to通道去
        long position = 0;
        long size = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, size);
        toFile.close();
        fromFile.close();
        System.out.println("over!");
    }
}
