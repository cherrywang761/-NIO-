package com.example.nio_demo.example.asyncfilechannel.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName FileChannelDemo2
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-19 23:16
 * @Version 1.0
 */
public class FileChannelDemo2 {
    //通过FileChannel实现写的操作
    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\86137\\Desktop\\test1\\FileChannelWriter.txt";
        //打开FileChannel
        RandomAccessFile aFile = new RandomAccessFile(fileName, "rw");
        FileChannel channel = aFile.getChannel();

        //创建buffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String newData = "FileChannelWriter";
        //清空buffer
        buffer.clear();
        //写入内容,把数据放到buffer中
        buffer.put(newData.getBytes());
        //读写模式的转换
        buffer.flip();
        //FileChannel完成最终的实现
        while(buffer.hasRemaining()){
            channel.write(buffer);
        }
        //关闭channel
        channel.close();
    }
}
