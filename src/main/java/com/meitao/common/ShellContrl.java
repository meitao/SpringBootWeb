package com.meitao.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShellContrl {

    static Logger logger = LogManager.getLogger(ShellContrl.class);

    public static void run() {

        String RUNNING_SHELL_FILE = "test.sh";   //程序路径
        String SHELL_FILE_DIR = "/home/sftp/";

        ProcessBuilder pb = new ProcessBuilder();
//        pb.directory(new File(SHELL_FILE_DIR));
        pb.command("bash", SHELL_FILE_DIR + RUNNING_SHELL_FILE);
        Process process;

//        Runtime runtime = Runtime.getRuntime();
        try {
            //调用服务器shell脚本

            process = pb.start();
//            process = runtime.exec("bash "+SHELL_FILE_DIR+RUNNING_SHELL_FILE);

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            executorService.execute(new ShellLog(process.getInputStream()));
            executorService.execute(new ShellLog(process.getErrorStream()));

            //获取脚本执行返回的信息
            boolean runningStatus = process.waitFor(1, TimeUnit.SECONDS);

            if (runningStatus) {
                logger.info("脚本执行成功!");

            } else
                logger.info("脚本执行超时!");

            executorService.shutdownNow();
            while (true){
                if(executorService.isTerminated()){
                    logger.info("关闭线程池!");
                    break;
                }
                Thread.sleep(1000);
            }





//            InputStream backStream = process.getInputStream();
//            br = new BufferedReader(new InputStreamReader(backStream));
//            String inline;
//            while ((inline = br.readLine()) != null) {
//                System.out.println(inline);
//            }
//            //关闭
//            br.close();

//            inline = null;
//            //获取脚本执行返回的错误信息
//            InputStream errorStream = process.getErrorStream();
//            br = new BufferedReader(new InputStreamReader(errorStream));
//            //错误信息
//            while ((inline = br.readLine()) != null) {
//                if (!inline.equals(""))
//                    System.out.println(inline);
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
