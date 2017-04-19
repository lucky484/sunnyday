package com.f2b2c.eco.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.f2b2c.eco.util.FileUtil;
import com.f2b2c.eco.util.PropertiesUtil;

/**
 * @Description 重写百度ue图片上传
 * @author Josen.yang
 * @date Dec 1, 2016 3:17:42 PM
 */

@Controller("ueuplodBgoodsController")
@RequestMapping("/api/ueuplod")
public class UeUploadImgController {

    @RequestMapping(value = "bgoogs-img", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> testUpload(Model model,
            @RequestParam(value = "upfile", required = false) MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Map<String, Object> result = new HashMap<String, Object>();//
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.copy(file.getBytes(), PropertiesUtil.getValue("fileSavePath"), "bGoods",
                FileUtil.getFileType(file.getOriginalFilename()));
        File targetFile = new File(filePath, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("name", file.getOriginalFilename());// 新的文件名
        result.put("originalName", file.getOriginalFilename());// 原始文件名
        result.put("size", file.getSize());
        result.put("state", "SUCCESS");
        result.put("url", PropertiesUtil.getValue("imgPath") + filePath);// 展示图片的请求url
        return result;
    }

    @RequestMapping(value = "b-notice-img", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bNoticeImg(Model model,
            @RequestParam(value = "upfile", required = false) MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Map<String, Object> result = new HashMap<String, Object>();//
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.copy(file.getBytes(), PropertiesUtil.getValue("fileSavePath"), "bNotice",
                FileUtil.getFileType(file.getOriginalFilename()));
        File targetFile = new File(filePath, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("name", file.getOriginalFilename());// 新的文件名
        result.put("originalName", file.getOriginalFilename());// 原始文件名
        result.put("size", file.getSize());
        result.put("state", "SUCCESS");
        result.put("url", PropertiesUtil.getValue("imgPath") + filePath);// 展示图片的请求url
        return result;
    }

    @RequestMapping(value = "b-wap-img", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bWapImg(Model model,
            @RequestParam(value = "upfile", required = false) MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Map<String, Object> result = new HashMap<String, Object>();//
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.copy(file.getBytes(), PropertiesUtil.getValue("fileSavePath"), "bWap",
                FileUtil.getFileType(file.getOriginalFilename()));
        File targetFile = new File(filePath, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("name", file.getOriginalFilename());// 新的文件名
        result.put("originalName", file.getOriginalFilename());// 原始文件名
        result.put("size", file.getSize());
        result.put("state", "SUCCESS");
        result.put("url", PropertiesUtil.getValue("imgPath") + filePath);// 展示图片的请求url
        return result;
    }

    @RequestMapping(value = "f-wap-img", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> fWapImg(Model model,
            @RequestParam(value = "upfile", required = false) MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Map<String, Object> result = new HashMap<String, Object>();//
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.copy(file.getBytes(), PropertiesUtil.getValue("fileSavePath"), "fWap",
                FileUtil.getFileType(file.getOriginalFilename()));
        File targetFile = new File(filePath, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("name", file.getOriginalFilename());// 新的文件名
        result.put("originalName", file.getOriginalFilename());// 原始文件名
        result.put("size", file.getSize());
        result.put("state", "SUCCESS");
        result.put("url", PropertiesUtil.getValue("imgPath") + filePath);// 展示图片的请求url
        return result;
    }

    /**
     * 帮助中心
     * 
     * @author mozzie.chu
     * @param model
     * @param file
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "b-help-img", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bHelpImg(Model model,
            @RequestParam(value = "upfile", required = false) MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Map<String, Object> result = new HashMap<String, Object>();//
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.copy(file.getBytes(), PropertiesUtil.getValue("fileSavePath"), "bHelp",
                FileUtil.getFileType(file.getOriginalFilename()));
        File targetFile = new File(filePath, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("name", file.getOriginalFilename());// 新的文件名
        result.put("originalName", file.getOriginalFilename());// 原始文件名
        result.put("size", file.getSize());
        result.put("state", "SUCCESS");
        result.put("url", PropertiesUtil.getValue("imgPath") + filePath);// 展示图片的请求url
        return result;
    }
}
