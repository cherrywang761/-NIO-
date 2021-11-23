package com.example.nio_demo.buffer;

import org.apache.tomcat.jni.Buffer;
import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @ClassName BufferDemo1
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-20 16:01
 * @Version 1.0
 */
public class BufferDemo1 {

    //Buffer读的示例
    @Test
    public void buffer01() throws Exception{
        String filePath = "C:\\Users\\86137\\Desktop\\test1\\from.txt";
        //打开channel
        RandomAccessFile aFile = new RandomAccessFile(filePath, "rw");
        FileChannel channel = aFile.getChannel();

        //创建一个Buffer,大小为1024
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //读,把数据写入buffer中了
        int bytesRead = 0;
        while(((bytesRead = channel.read(buffer)) != -1)) {
            //翻转buffer，开始读取数据
            buffer.flip();
            //如果这里面还有数据，我们就继续输出

            while (buffer.hasRemaining()) {
                System.out.println((char)buffer.get());
            }
            buffer.clear();
        }
        aFile.close();
    }


    //IntBuffer读的示例
    @Test
    public void buffer02(){
        //创建一个IntBuffer
        IntBuffer buffer = IntBuffer.allocate(8);

        //往buffer中放数据
        int sum = 0;
        for(int i = 0; i < buffer.capacity(); i++){
            sum += 2*(i + 1);
            buffer.put(sum);
        }

        //翻转，重置缓冲区
        buffer.flip();
        //获取
        while(buffer.hasRemaining()){
            int value = buffer.get();
            System.out.println(value + " ");
        }
    }
}
