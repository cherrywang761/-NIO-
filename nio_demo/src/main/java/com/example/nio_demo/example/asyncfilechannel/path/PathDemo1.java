package com.example.nio_demo.example.asyncfilechannel.path;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName PathDemo1
 * @Description TODO
 * @Author 86137
 * @Date 2021-11-22 23:20
 * @Version 1.0
 */
public class PathDemo1 {
    public static void main(String[] args) {
        //创建Path实例(绝对路径）
        Path path = Paths.get("C:\\Users\\86137\\Desktop\\test1\\PathDemo.txt");

        //创建Path实例（绝对路径）
        Path projects = Paths.get("d:\\atguigu", "projects");
    }
}
