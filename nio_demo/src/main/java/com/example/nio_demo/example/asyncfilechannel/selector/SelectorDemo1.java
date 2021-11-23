package com.example.nio_demo.example.asyncfilechannel.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName SelectorDemo1
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-21 22:18
 * @Version 1.0
 */
public class SelectorDemo1 {
    public static void main(String[] args) throws IOException {
        //创建selector
        Selector selector = Selector.open();

        //创建通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //channel想注册到selector中，首先它必须要处于非阻塞模式下
        serverSocketChannel.configureBlocking(false);

        //绑定连接和端口
        serverSocketChannel.bind(new InetSocketAddress(9999));

        //将通道注册到选择器上,并制定监听事件为："接收"事件
        //可以通过通道的validOps()方法，获取特定通道所有支持的操作集合
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //我们上面那个serverSocketChannel通道已经在选择器中进行注册
        //我们可以查询已经就绪通道操作,放到选择键集合中（每个通道的每个就绪操作）
        /**
         *  最好在selectedKeys()之前，用Selector.select()方法，查询已经就绪的操作，
         *  返回int值表示有多个通道就绪, 一旦调用select()方法返回值不为0，就调用selectedKeys()
         *  返回已就绪的选择键集合
         */
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        //遍历集合, 轮询查询就绪操作
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while(iterator.hasNext()){
            SelectionKey key = iterator.next();
            //判断选择键 key 的就绪状态操作
            //是否是“可接受”状态
            if(key.isAcceptable()){
                //...实现可接收逻辑功能
            }
            //是否是“可连接”状态
            else if(key.isConnectable()){
                //...实现可连接逻辑功能
            }
            //是否是“可读”状态
            else if(key.isReadable()){
                //...实现可读逻辑功能
            }
            //是否是“可读”状态
            else if(key.isWritable()){
                //...实现可写逻辑功能
            }
            iterator.remove();
        }
    }
}
