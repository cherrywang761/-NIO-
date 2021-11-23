package com.example.nio_demo.example.asyncfilechannel.selector;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName SelectorDemo2
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-21 22:56
 * @Version 1.0
 */
public class SelectorDemo2 {

    //服务端代码
    @Test
    public void serverDemo() throws IOException {
        //1.获取服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.切换成非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //3.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //4.绑定主机和端口号
        serverSocketChannel.bind(new InetSocketAddress(8080));
        //5.获取selector选择器
        Selector selector = Selector.open();
        //6.通道注册到选择器中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //7.选择器进行轮询，进行监听
        while(selector.select() > 0){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //轮询遍历
            while(iterator.hasNext()){
                //获取到已经就绪的操作
                SelectionKey key = iterator.next();
                /**
                 * 判断这个轮询获取的key是什么就绪操作,进行相关的业务处理
                 * （这里为了简单只放了一个channel，实际上selector中可以注册很多channel进来的）
                 */
                if(key.isAcceptable()){
                    //获取连接
                    SocketChannel accept = serverSocketChannel.accept();
                    //切换到非阻塞模式
                    accept.configureBlocking(false);
                    //把接收到的客户端channel注册到选择器中实现非阻塞功能
                    accept.register(selector, SelectionKey.OP_READ);
                }
                else if(key.isReadable()){
                    SocketChannel readAbleChannel = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    //读取数据
                    int readByte = 0;
                    while((readByte = readAbleChannel.read(buffer)) != -1){
                        readBuffer.flip();
                        System.out.println(new String(buffer.array(), 0, readByte));
                        readBuffer.clear();
                    }
                }
            }
            iterator.remove();
        }
    }



    //客户端代码
    @Test
    public void clientDemo() throws IOException {
        //1.获取通道，绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
        //2.切换到非阻塞模式
        socketChannel.configureBlocking(false);
        //3.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //4.写入buffer数据
        buffer.put("我是nonbioclock".getBytes(StandardCharsets.UTF_8));
        //5.模式切换
        buffer.flip();
        //6.写入通道
        socketChannel.write(buffer);
        //7.关闭
        buffer.clear();
    }

    public static void main(String[] args) {

    }
}
