package com.example.nio_demo.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

/**
 * @ClassName CharsetDemo
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-23 22:28
 * @Version 1.0
 */
public class CharsetDemo {

    public static void main(String[] args) throws CharacterCodingException {
        //1.获取Charset对象
        Charset charset = StandardCharsets.UTF_8;

        //2.获取编码器对象
        CharsetEncoder charsetEncoder = charset.newEncoder();

        //3.创建缓冲区
        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("我是nonbioclock");
        buffer.flip();

        //4.编码
        ByteBuffer encodeBuffer = charsetEncoder.encode(buffer);
        System.out.println("编码后结果:");
        for (int i = 0; i < encodeBuffer.limit(); i++) {
            System.out.println(encodeBuffer.get());
        }

        //5.解码
        encodeBuffer.flip();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer decodeBuffer = charsetDecoder.decode(encodeBuffer);
        System.out.println("解码后结果：");
        System.out.println(decodeBuffer.toString());
    }
}
