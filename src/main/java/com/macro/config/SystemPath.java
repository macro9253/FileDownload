package com.macro.config;


import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;


/**
 * 文件路径与文件上传下载  都是采用流的方式
 * 注意： 需要在yml中设置上传文件大小，springboot有控制的
 *
 */
public class SystemPath {
    public static String BasePath = "";
    //根目录
    private static String LINUX = "/";
    private static String WINDOWS = "C:/";
    private static String MACPATH = "/";

    static {
        initBasePath();
    }

    /**
     * 系统初始化下载地址
     */
    public static void initBasePath() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            BasePath = LINUX;
        } else if (os != null && os.toLowerCase().indexOf("mac") > -1) {
            BasePath = MACPATH;
        } else {
            BasePath =  WINDOWS;
        }
    }

    /**
     * 下载
     * @param path  下载路径  win: C:/XXX/XXXX.zip  ,Linux: /XXXXX/XXXXX.zip
     * @param response
     * @return
     */

    public static HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
        // 下载本地文件
        String fileName = "Operator.doc".toString(); // 文件的默认保存名
        // 读到流中
        InputStream inStream = new FileInputStream("c:/Operator.doc");// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
