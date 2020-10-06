package com.macro.utils;

import com.macro.config.SystemPath;
import com.macro.entity.CatalogueEntity;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 核心点: 加载文件列表
 */
public class FileUtils {

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

    public static List<CatalogueEntity> getFileList(String path){
        path  = StringUtils.isEmpty(path)? SystemPath.BasePath: path;
        List<CatalogueEntity> list = new ArrayList<>();
        File file = new File(path);
        for (File oneFile :file.listFiles()) {
            if(!oneFile.isHidden() && oneFile.canWrite()){
            if(oneFile.exists()&& oneFile.isFile()){
                list.add(new CatalogueEntity("file",oneFile.getName(),formatFileSize(oneFile.length()),1));
            }else{
                try{
                    File[] files = oneFile.listFiles();
                    list.add(new CatalogueEntity("folder",oneFile!=null?oneFile.getName():"", "包含" + files.length + "个文件",0));
                }catch (NullPointerException e){ //win有些文件读取有毒，会发生空指针
                }
            }
            }
        }
        return list;
    }



}
