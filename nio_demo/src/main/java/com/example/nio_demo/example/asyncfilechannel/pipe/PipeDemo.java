package com.example.nio_demo.example.asyncfilechannel.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @ClassName PipeDemo
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-22 21:39
 * @Version 1.0
 */
public class PipeDemo {
    public static void main(String[] args) throws IOException {
        //1.获取管道
        Pipe pipe = Pipe.open();
        //2.获取sink通道
        Pipe.SinkChannel sinkChannel = pipe.sink();
        //3.创建缓冲区
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put("helloworld.".getBytes());
        writeBuffer.flip();
        //4.写入数据
        sinkChannel.write(writeBuffer);

        //5.获取source通道
        Pipe.SourceChannel sourceChannel = pipe.source();
        //6.创建缓冲区，读取数据
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int length = sourceChannel.read(readBuffer);
        System.out.println(new String(readBuffer.array(), 0, length));
        //7.关闭通道
        sourceChannel.close();
        sinkChannel.close();
    }
}
