package com.etoak.controller;

import com.etoak.entity.ShortVideo;
import com.etoak.entity.Video;
import com.etoak.service.VideoService;
import com.etoak.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    VideoService videoService;

    @GetMapping("/getAllVideos")
    public ResultVO getAllVideos(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                 @RequestParam(required = false,defaultValue = "6")Integer pageSize,
                                 Video video){
        return videoService.getAllVideos(pageNum,pageSize,video);
    }

    @GetMapping("/queryAllShortVideo")
    public ResultVO queryAllShortVideo(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                       @RequestParam(required = false,defaultValue = "3")Integer pageSize,
                                       ShortVideo shortVideo){
        return videoService.queryAllShortVideo(pageNum,pageSize,shortVideo);
    }

    @PostMapping("/uploadImg")
    public ResultVO upload( MultipartFile file){
        return videoService.upload(file);
    }
}
