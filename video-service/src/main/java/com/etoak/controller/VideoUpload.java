package com.etoak.controller;

import com.etoak.vo.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/video")
public class VideoUpload {

    @Value("${ipPath.path}")
    private String ipPath;

    @PostMapping("/upload")
    public ResultVO upload(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        // String end = FilenameUtils.getExtension(originalFilename);
        String end = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String pre = UUID.randomUUID().toString().replaceAll("-", "");
        String newName = pre + "." + end;
        String path = "";
        String responsePath = "";
        if (file.getContentType().contains("image")) {
            path = "D:/upload/uniapp/img";
            responsePath = ipPath +  "/rst-video/pics/" + newName;
        }else {
            path = "D:/upload/uniapp/video";
            responsePath = ipPath + "/rst-video/videos/" + newName;
        }
        File f = new File(path, newName);
        try {
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVO.success(responsePath);
    }

    /*前端改进后不点发布视频后台不会存储图片 针对发布视频功能*/
    @RequestMapping("/upload2")
    public ResultVO upload2(@RequestParam("file") MultipartFile file,String fileName ){
        if(file != null){
            String path = "D:/upload/uniapp/video";
            File f = new File(path,fileName);
            try {
                file.transferTo(f);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultVO.error();
            }
        }
        return ResultVO.success();
    }


}
