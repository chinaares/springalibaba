package com.example.thymeleaf.controller;

import com.example.thymeleaf.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @GetMapping("home")
    public String index(Model model) {
        User user = new User();
        user.setUsername("jack");
        user.setPassword("112233");
        user.setHobbies(Arrays.asList(new String[]{"singing", "dancing", "football"}));
        Map<String, String> maps = new HashMap<>();
        maps.put("1", "o");
        maps.put("2", "g");
        maps.put("3", "a");
        maps.put("4", "j");
        user.setSecrets(maps);
        model.addAttribute("user", user);
        return "index";
    }


    @Value("${uploadFile.resourceHandler}")
    private String resourceHandler;//请求 url 中的资源映射，不推荐写死在代码中，最好提供可配置，如 /uploadFiles/**
    @Value("${uploadFile.location}")
    private String uploadFileLocation;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值，如 E:/wmx/uploadFiles/
    /**
     * 文件上传，因为只是演示，所以使用 @ResponseBody 将结果直接返回给页面
     *
     * @param multipartFile
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("uploadFile")
    @ResponseBody
    public String uploadFile(MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return "上传文件为空...";
        }
        //basePath拼接完成后，形如：http://192.168.1.20:8080/fileServer
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String fileName = multipartFile.getOriginalFilename();
        String fileServerPath = basePath + resourceHandler.substring(0, resourceHandler.lastIndexOf("/") + 1) + fileName;
        System.out.println("文件访问路径：" + fileServerPath);

        File saveFile = new File(uploadFileLocation, fileName);
        multipartFile.transferTo(saveFile);//文件保存

        System.out.println("文件保存路径：" + saveFile.getPath());

        return "<a href='" + fileServerPath + "'>" + fileServerPath + "</a>";
    }
}