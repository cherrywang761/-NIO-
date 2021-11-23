package com.example.nio_demo.chat.client;

import java.io.IOException;

/**
 * @ClassName AClient
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-24 0:04
 * @Version 1.0
 */
public class AClient {
    public static void main(String[] args) {
        try {
            new ChatClient().startClient("Lucy");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
