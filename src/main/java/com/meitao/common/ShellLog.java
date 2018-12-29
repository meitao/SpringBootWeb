package com.meitao.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 打印日志线程
 */
public class ShellLog implements Runnable {

    private InputStream inputStream;

    public ShellLog(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        String inline;
        //获取脚本执行返回的错误信息
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        //错误信息
        try {
            while ((inline = br.readLine()) != null) {
                if (!inline.equals(""))
                    System.out.println(inline);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
