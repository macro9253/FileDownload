package com.macro.controller;


import com.macro.annotation.LoginCookie;
import com.macro.config.SystemPath;
import com.macro.utils.FileUtils;
import com.macro.utils.RequestUtils;
import com.macro.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 *
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserUtils userUtils;

    //登陆
    @PostMapping("user/login")
    public String pageLogin(String username, String password,HttpServletRequest request, HttpServletResponse response){
        if(!userUtils.getUsername().equals(username)){
            return "login";
        }
        if(!userUtils.getPassword().equals(password)){
            return "login";
        }
        /**
         * 返回前端数据
         */
        Cookie cookie = RequestUtils.getCookie(request.getCookies());
        System.err.println("#####" + cookie.getValue());
        response.addCookie(cookie);
        /**
         * 缓存本地数据
         */
        request.getSession().setAttribute(cookie.getValue(),cookie.getValue());
        return "redirect:/";
    }
    //进入登陆页面
    @GetMapping("login")
    public String login(){
        return "login";
    }

    //进入首页 aop检测登陆状态
    @LoginCookie
    @GetMapping(path = {"index","/"})
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("index");
        view.addObject("name",userUtils.getName());
        view.addObject("system",System.getProperties().getProperty("os.name"));
        return view;
    }
    //退出
    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONIDS")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return "login";
    }

    //list页面
    @LoginCookie
    @GetMapping("list")
    public String list(){
        return "system/list";
    }
    //home页面
    @LoginCookie
    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView view = new ModelAndView("/system/home");
        view.addObject("name",userUtils.getName());
        view.addObject("system",System.getProperties().getProperty("os.name"));
        view.addObject("path",SystemPath.BasePath);
        return view;
    }
    //download页面默认页面
    @LoginCookie
    @GetMapping("download")
    public String download(){
        return "system/download";
    }

    @LoginCookie
    @GetMapping("download/getTable")
    @ResponseBody
    public Map<String,Object> getTableData(String path){
        Map<String,Object> map = new HashMap<>();
        map.put("path", StringUtils.isEmpty(path)? SystemPath.BasePath: path);
        map.put("list",FileUtils.getFileList(path));
        return map;
    }

    @LoginCookie
    @GetMapping("download/file")
    public void downloadFile(String path, HttpServletResponse response){

        log.info("获取到的当前下载路径为: {}",path);
        SystemPath.download(path,response);
    }

    /**
     * 上传文件
     */
    @RequestMapping( value="/upload", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadImg(@RequestParam("file") MultipartFile file,String path){
        Map<String,Object> map = new HashMap<>();
        log.info("文件上传的位置: {}" ,path);
        try {
            //图片名上传
            String   name = file.getOriginalFilename();
            File dest = new File(path + name);
            dest.createNewFile();//创建一个文件
            FileOutputStream fot = new FileOutputStream(dest);
            byte[] bytes=file.getBytes();
            try {
                fot.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fot.close();
            map.put("code",200);
            map.put("msg","上传成功");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",300);
            map.put("msg","上传失败");
            return map;
        }
    }



}
