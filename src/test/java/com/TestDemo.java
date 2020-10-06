package com;

import com.macro.entity.CatalogueEntity;
import com.macro.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class TestDemo {


    //isFile:true 文件   false文件夹
    @Test
    public void test(){
        List<CatalogueEntity> fileList = FileUtils.getFileList("C:\\");
        System.out.println("list: " + fileList.size());
        for (CatalogueEntity entity: fileList) {
            System.out.println(entity.toString());
        }

    }


    public static String formatFileSize(long size) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

}
