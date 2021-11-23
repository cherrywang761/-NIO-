package com.example.nio_demo.example.asyncfilechannel.channel;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName ServerSocketChnnelDemo
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-20 13:35
 * @Version 1.0
 */
public class ServerSocketChnnelDemo{
    //ServerSocketChannel使用示例
    public static void main(String[] args) throws Exception{
        //端口号设置
        int port = 8888;
        //设置一个缓冲区
        //wrap()方法：把字节数组包装成ByteBuffer对象
        ByteBuffer buffer = ByteBuffer.wrap("hello world!".getBytes());
        //获取ServerSocketChannel对象
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定端口（因为ServerSocketChannel中没有bind()方法，需要获取到对等socket，也就是ServerSocket)
        ServerSocket socket = ssc.socket();
        socket.bind(new InetSocketAddress(port));
        //设置一个非阻塞模式运行
        ssc.configureBlocking(false);
        //一直监听是否有新的连接进入
        while(true){
            System.out.println("Waiting for connect");
            //如果是阻塞模式，那么会一直在accept()这里阻塞，根本走不下去
            SocketChannel sc = ssc.accept();
            //没有连接传入
            if(sc == null){
                System.out.println("null");
                Thread.sleep(2000);
            }else{
                //有连接传入
                System.out.println("Incoming connection from: " + sc.socket().getInetAddress());
                //表示缓冲区指针，指向了0
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
