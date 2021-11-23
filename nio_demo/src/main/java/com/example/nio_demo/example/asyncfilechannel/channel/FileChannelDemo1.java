package com.example.nio_demo.example.asyncfilechannel.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName FileChannel
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-19 22:21
 * @Version 1.0
 */
public class FileChannelDemo1 {
    //FileChannel读取数据到buffer
    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\86137\\Desktop\\test1\\hello.txt";
        //1. 创建FileChannel
        //FileChannel并没有直接方法创建，我们需要通过文件进行创建
        /**
         * RandomAccessFile:
         * 我们平常创建流对象关联文件,开始读文件或者写文件都是从头开始的,不能从中间开始,
         * 如果是开多线程下载一个文件,我们之前学过的FileWriter或者FileReader等等都无法完成,
         * 而当前介绍的RandomAccessFile他就可以解决这个问题
         * 因为它可以指定位置读,指定位置写的一个类
         * 通常开发过程中,多用于多线程下载一个大文件.
         */
        RandomAccessFile aFile =
                new RandomAccessFile(fileName, "rw");       //rw：读写模式
        FileChannel channel = aFile.getChannel();

        //2. 创建Buffer(分配给它的大小为1024)
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3. 读取数据到buffer中
        int bytesRead = channel.read(buf);
        while(bytesRead != -1){
            //表示有内容
            System.out.println("读取了：" + bytesRead);
            //读写模式的转换
            buf.flip();
            //表示buffer中是否有剩余的内容
            while(buf.hasRemaining()){
                //如果有，我们把它拿出来
                System.out.println((char)buf.get());
            }
            buf.clear();
            //如果还有的话，继续操作
            bytesRead = channel.read(buf);
        }
        aFile.close();
        System.out.println("操作结束");
    }
}
