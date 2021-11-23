package com.example.nio_demo.example.asyncfilechannel.buffer;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;

/**
 * @ClassName BufferDemo2
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-20 19:43
 * @Version 1.0
 */
public class BufferDemo2 {

    @Test
    public void b01(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for(int i = 0; i < buffer.capacity(); i++){
            buffer.put((byte)i);
        }
        buffer.position(3);
        buffer.limit(7);
        //创建一个子缓冲区
        ByteBuffer slice = buffer.slice();
        for(int i = 0; i < slice.capacity(); i++){
            byte b = slice.get(i);
            b *= 10;
            slice.put(i,b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }


    //只读缓冲区
    @Test
    public void b02(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for(int i = 0; i < buffer.capacity(); i++){
            buffer.put((byte)i);
        }

        //创建一个只读缓冲区
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        for (int i = 0; i < readOnlyBuffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *= 10;
            buffer.put(i, b);
        }
        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(buffer.capacity());
        //遍历只读缓冲区，看看会不会值会不会随之变化
        while(readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        try{
            readOnlyBuffer.put(1,(byte)0);
        }catch (ReadOnlyBufferException e){
            System.out.println("只读缓冲区不能写入数据");
            e.printStackTrace();
        }
    }

    //直接缓冲区
    @Test
    public void b03() throws IOException {
        String filePath = "C:\\Users\\86137\\Desktop\\test1\\hello.txt";
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel finChannel = fis.getChannel();

        String outFile = "C:\\Users\\86137\\Desktop\\test1\\directBuffer.txt";
        FileOutputStream fout = new FileOutputStream(outFile);
        FileChannel foutChannel = fout.getChannel();

        //创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while(true){
            buffer.clear();
            int r = finChannel.read(buffer);
            if(r == -1){
                break;
            }
            buffer.flip();
            foutChannel.write(buffer);
        }
    }

    static private final int start = 0;
    static private final int size = 1024;
    //内存映射文件IO
    @Test
    public void b04() throws Exception{
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\86137\\Desktop\\test1\\hello.txt", "rw");
        FileChannel fc = raf.getChannel();
        //MappedByteBuffer，对内存映射做操作的缓冲容器, rw模式
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
        mbb.put(0, (byte)97);
        mbb.put(1023, (byte)122);
        raf.close();
    }
}
