package com.macro.entity;

import lombok.Data;

/**
 * 封装文件列表
 */
@Data
public class CatalogueEntity {
    //图标
    private String icon;
    //名称
    private String filename;
    //文件大小或者包含数量
    private String size;

    private String classs;
    //0文件夹 1文件
    private Integer state;

    public CatalogueEntity(String icon,String filename,String size,Integer state){
        this.icon = "/img/" + icon + ".png";
        this.filename = filename;
        this.size = size;
        this.state = state;
        this.classs = state.equals(0)?"li-folder":"li-file";
    }






}
