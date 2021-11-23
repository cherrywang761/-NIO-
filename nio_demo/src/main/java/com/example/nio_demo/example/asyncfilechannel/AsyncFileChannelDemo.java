package com.example.nio_demo.example.asyncfilechannel;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * @ClassName AsyncFileChannelDemo
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-23 0:03
 * @Version 1.0
 */
public class AsyncFileChannelDemo {


    //通过Future实现异步读文件的功能
    @Test
    public void readAsyncFileChannelFuture() throws IOException {
        //1.创建AsynchronousFileChannel对象
        Path path = Paths.get("C:\\Users\\86137\\Desktop\\test1\\hello.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        //2.创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //3.调用channel的read方法得到Future
        Future<Integer> future = channel.read(buffer, 0);
        //4.判断是否完成isDone,返回true
        while (!future.isDone()) {
        }
        //5.读取数据到buffer里面
        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }


    //通过Future实现异步读文件的功能
    @Test
    public void readAsyncFileChannelCompletionHandler() throws IOException {
        //1.创建AsynchronousFileChannel对象
        Path path = Paths.get("C:\\Users\\86137\\Desktop\\test1\\hello.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        //2.创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //3.调用channel的read方法得到Future
        channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            //表示读取完成后调这个方法
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result: " + result);
                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            //该方法是读取失败后会调用
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }


    @Test
    public void writeAsyncFileFuture() throws IOException {
        //1.创建AsynchronousFileChannel对象
        Path path = Paths.get("C:\\Users\\86137\\Desktop\\test1\\asyncChannel.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        //2.创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put("我是nonbioclock".getBytes(StandardCharsets.UTF_8));
        buffer.flip();

        Future<Integer> future = channel.write(buffer, 0);
        while(!future.isDone());
        buffer.clear();
        System.out.println("write over");
    }


    @Test
    public void writeAsyncFileChannelCompletionHandler() throws IOException{
        //1.创建AsynchronousFileChannel对象
        Path path = Paths.get("C:\\Users\\86137\\Desktop\\test1\\asyncChannel.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        //2.创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //3.write方法
        buffer.put("helloworld.".getBytes());
        buffer.flip();

        channel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("bytes written: " + result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
        System.out.println("write over!");
    }
}
