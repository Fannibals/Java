package com.company;

import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;



public class Main {

    public static String Test_URL = "http://127.0.0.1:5000/getTest";

    public static void main(String[] args) throws Exception{
        //获取本机InetAddress的实例：
//        InetAddress address = InetAddress.getLocalHost();
//        System.out.println("本机名：" + address.getHostName());
//        System.out.println("IP地址：" + address.getHostAddress());
//        byte[] bytes = address.getAddress();
//        System.out.println("字节数组形式的IP地址：" + Arrays.toString(bytes));
//        System.out.println("直接输出InetAddress对象：" + address);

        System.out.println(getString(Test_URL));
    }

    private static String getString(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置连接超时为5秒
        conn.setConnectTimeout(5000);
        // 设置请求类型为Get类型
        conn.setRequestMethod("GET");
        // 判断请求Url是否成功
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("请求url失败");
        }
        InputStream inStream = conn.getInputStream();
        String result = convertToString(inStream);
        inStream.close();
        return result;
    }

    private static String convertToString(InputStream inStream) throws Exception{
        byte[] bytes = new byte[0];
        bytes = new byte[inStream.available()];
        inStream.read(bytes);
        String result = new String(bytes);
        return result;
    }
}



