package com.example.nio_demo.chat.server;

import com.sun.org.apache.bcel.internal.generic.Select;
import org.apache.catalina.core.StandardContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName ChatServer
 * @Description 聊天室服务器
 * @Author 86137
 * @Date 2021-11-23 22:46
 * @Version 1.0
 */
public class ChatServer {
    //服务器端启动的方法
    public void startServer() throws IOException {
        //1.创建一个Selector选择器
        Selector selector = Selector.open();
        //2.创建ServerSocketChannel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //3.为通道绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //注册到selector中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已经启动成功");

        //4.循环等待有新连接接入
        for (; ; ) {
            //获取channel数量
            int readChannels = selector.select();
            if (readChannels == 0) {
                continue;
            }
            //获取可用的channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历集合
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                //移除set的当前selectionKey
                iterator.remove();
                //5.根据就绪状态，调用对应方法实现具体业务操作
                //5.1如果accept状态
                if (selectionKey.isAcceptable()) {
                    acceptOperator(serverSocketChannel, selector);
                }
                //5.2如果可读状态
                else if (selectionKey.isReadable()) {
                    readOperator(selector, selectionKey);
                }
            }
        }
    }

    //处理可读状态的操作
    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException {
        //1.从selectionKey获取到已经就绪的通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //2.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //3.循环读取客户端消息
        int readLength = socketChannel.read(buffer);
        String message = "";
        if (readLength > 0) {
            //切换读模式
            buffer.flip();
            //读取内容
            message += StandardCharsets.UTF_8.decode(buffer);
        }
        //4.channel继续注册进去，监听可读的状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        //5.把客户端发送消息，广播到其他客户端
        if(message.length()>0){
            //广播给其他客户端
            System.out.println(message);
            castOtherClient(message, selector, socketChannel);
        }
    }

    //广播给其他的客户端
    private void castOtherClient(String message, Selector selector, SocketChannel socketChannel) throws IOException {
        //1.获取所有已经接入的客户端
        Set<SelectionKey> selectionKeySet = selector.keys();

        //2.循环向所有channel广播消息
        for (SelectionKey selectionKey : selectionKeySet) {
            //获取每个channel
            Channel targetChannel = selectionKey.channel();
            //广播(剔除自己）
            if(targetChannel instanceof SocketChannel && targetChannel != socketChannel){
                ((SocketChannel) targetChannel).write(StandardCharsets.UTF_8.encode(message));
            }
        }
    }

    //处理接入状态操作
    private void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        //1.接入状态，创建一个socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        //2.非阻塞模式
        socketChannel.configureBlocking(false);
        //3.把channel注册到selector选择器上面
        socketChannel.register(selector, SelectionKey.OP_READ);
        //4.客户端回复信息
        socketChannel.write(StandardCharsets.UTF_8.encode("欢迎进入聊天室，请注意隐私安全！"));
    }

    public static void main(String[] args) {
        //程序入口
        try {
            new ChatServer().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
