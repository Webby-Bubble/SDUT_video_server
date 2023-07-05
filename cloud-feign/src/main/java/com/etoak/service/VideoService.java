package com.etoak.service;

import com.etoak.entity.ShortVideo;
import com.etoak.entity.Video;
import com.etoak.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("video-service")
public interface VideoService {

    @PostMapping("/rst-video/video/getAllVideos")
    ResultVO getAllVideos(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody Video video);

    @PostMapping("/rst-video/video/queryAllShortVideo")
    ResultVO queryAllShortVideo(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody ShortVideo shortVideo);

    @PostMapping("/rst-video/video/upload")
    ResultVO upload(@RequestPart MultipartFile file);
}
