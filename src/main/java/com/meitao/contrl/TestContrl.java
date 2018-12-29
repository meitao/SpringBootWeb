package com.meitao.contrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestContrl {

    @RequestMapping("/hello")
    public String hello() {
        String RUNNING_SHELL_FILE = "test.sh";   //程序路径
        String SHELL_FILE_DIR = "/home/sftp/";

//
//        Process process = Runtime.getRuntime().exec(command1);
//        process.waitFor();

//        String command = "/bin/sh " + shpath;
//        try {
//            Runtime.getRuntime().exec(command2).waitFor();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        ProcessBuilder pb = new ProcessBuilder("./" + RUNNING_SHELL_FILE);
        pb.directory(new File(SHELL_FILE_DIR));

        Process process;
        BufferedReader br = null;
        try {
            //调用服务器shell脚本
            process = pb.start();
//            process = runtime.exec(command);
            //获取脚本执行返回的信息

            InputStream backStream = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(backStream));
            String inline;
            while ((inline = br.readLine()) != null) {
                System.out.println(inline);
            }
            //关闭
            br.close();

            inline = null;
            //获取脚本执行返回的错误信息
            InputStream errorStream = process.getErrorStream();
            br = new BufferedReader(new InputStreamReader(errorStream));
            //错误信息
            while ((inline = br.readLine()) != null) {
                if (!inline.equals(""))
                    System.out.println(inline);
            }
           boolean runningStatus = process.waitFor(10, TimeUnit.SECONDS) ;

            if (runningStatus){
                System.out.println("脚本执行成功!");

            }else
                System.out.println("脚本执行失败!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "hello";

    }
}
