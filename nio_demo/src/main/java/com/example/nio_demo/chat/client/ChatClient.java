package com.example.nio_demo.chat.client;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @ClassName ChatClient
 * @Description 聊天室客户端
 * @Author 86137
 * @Date 2021-11-23 22:46
 * @Version 1.0
 */
public class ChatClient {

    //启动客户端方法
    public void startClient(String name) throws IOException {
        //1.连接服务器端
        SocketChannel socketChannel =
                SocketChannel.open(new InetSocketAddress(9999));

        //接收服务端响应数据
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        //创建线程
        new Thread(new ClientThread(selector)).start();

        //向服务端发送消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String msg
                    = scanner.nextLine();
            if(StringUtils.hasText(msg)){
                socketChannel.write(StandardCharsets.UTF_8.encode(name + ":" + msg));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatClient().startClient("Bob");
    }
}
