package com.example.nio_demo.example.asyncfilechannel.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @ClassName SocketChannelDemo
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-20 14:02
 * @Version 1.0
 */
public class SocketChannelDemo {
    //SocketChannel使用示例
    public static void main(String[] args) throws Exception {
        //创建SocketChannel有两种方法，有参open()和无参open()
//        //第一种，使用有参open()
//        SocketChannel socketChannel1 = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));

        //第二种,无参open只是创建了一个SocketChannel对象，并没有进行实质的tcp连接
        SocketChannel socketChannel2 = SocketChannel.open();
        socketChannel2.connect(new InetSocketAddress("www.baidu.com", 80));

        //设置为非阻塞模式
        socketChannel2.configureBlocking(false);

        //SocketChannel读写，记住，所有读写都是面向缓冲区的
        ByteBuffer buffer = ByteBuffer.allocate(16);
        socketChannel2.write(buffer);
        socketChannel2.close();
        System.out.println("read over");
    }
}
