package com.example.nio_demo.example.asyncfilechannel.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName DatagramChannelDemo
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-20 14:21
 * @Version 1.0
 */
public class DatagramChannelDemo {

    //DatagramChannel使用示例
    //数据报发送的示例
    @Test
    public void sendDatagram() throws IOException, InterruptedException {
        //打开一个DatagramChannel
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 9999);

        //发送
        while(true){
            ByteBuffer buffer = ByteBuffer.wrap("你好 DatagramChannel!".getBytes(StandardCharsets.UTF_8));
            sendChannel.send(buffer, sendAddress);
            System.out.println("UDP已经完成发送");
            Thread.sleep(5000);
        }
    }

    @Test
    public void receiveDatagram() throws IOException {
        //打开DatagramChannel（UDP数据报不区分服务端客户端的）
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress(9999);

        //绑定监听地址
        receiveChannel.bind(receiveAddress);
        //创建缓冲区
        ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
        //接收
        while(true){
            receiveBuffer.clear();
            SocketAddress socketAddress = receiveChannel.receive(receiveBuffer);
            //模式的转换，flip()一般用于读数据时使用，把limit设成position，position设为0
            receiveBuffer.flip();
            //打印发送数据的地址信息
            System.out.println(socketAddress.toString());
            //打印存放于缓冲区的数据信息
            System.out.println(StandardCharsets.UTF_8.decode(receiveBuffer));
        }
    }


    //连接 read 和 write
    @Test
    public void testConnect() throws Exception{
        DatagramChannel connChannel = DatagramChannel.open();
        connChannel.bind(new InetSocketAddress(9999));

        //连接
        connChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

        //write方法
        ByteBuffer writeBuffer = ByteBuffer.wrap("发送数据helloworld.".getBytes(StandardCharsets.UTF_8));
        connChannel.write(writeBuffer);

        //read方法
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        while(true){
            readBuffer.clear();
            connChannel.read(readBuffer);
            readBuffer.flip();
            System.out.println(StandardCharsets.UTF_8.decode(readBuffer));
        }
    }
}

