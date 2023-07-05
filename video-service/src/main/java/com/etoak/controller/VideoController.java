package com.etoak.controller;

import com.etoak.dto.ReportDto;
import com.etoak.dto.ShortVideoDto;
import com.etoak.entity.*;
import com.etoak.mqConfig.RabbitMQConfig;
import com.etoak.service.VideoService;
import com.etoak.vo.ResultVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/getAllVideos")
    public ResultVO getAllVideos(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                 @RequestParam(required = false,defaultValue = "6")Integer pageSize,
                                 Video video){

        Page<Video> videoVoPage = videoService.getVideos(pageNum,pageSize,video);
        return ResultVO.success(videoVoPage);
    }

    @GetMapping("/queryAllShortVideo")
    public ResultVO queryAllShortVideo(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                       @RequestParam(required = false,defaultValue = "3")Integer pageSize, ShortVideo shortVideo){

        Page<ShortVideoDto> shortVideoPage = videoService.queryAllShortVideo(pageNum,pageSize,shortVideo);
        return ResultVO.success(shortVideoPage);
    }

    @PostMapping("/addVideoReport")
    public ResultVO addVideoReport(@RequestBody ReportVo reportVo){
        int i =  videoService.addVideoReport(reportVo);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }
    @GetMapping("/queryAllReportInfoByStatus")
    public ResultVO queryAllReportInfoByStatus(@RequestParam Integer userId,@RequestParam Integer reportStatus){
        List<ReportDto> reportVoList =  videoService.queryAllReportInfoByStatus(userId,reportStatus);
        return ResultVO.success(reportVoList);
    }

    @GetMapping("/queryAllReportInfoList")
    public ResultVO queryAllReportInfoList(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                           @RequestParam(required = false,defaultValue = "3")Integer pageSize, Report report){
        Page<ReportDto> reportDtoPage = videoService.queryAllReportInfoList(pageNum,pageSize,report);
        return ResultVO.success(reportDtoPage);
    }

    @GetMapping("/queryReportResultByid")
    public ResultVO queryReportResultByid(@RequestParam Integer reportId){
       ReportDto reportDto =  videoService.queryReportResultByid(reportId);
       if(reportDto != null)return ResultVO.success(reportDto);
       return ResultVO.error();
    }

    @PostMapping("/updateReportExamine")
    public ResultVO updateReportExamine( @RequestBody ReportVo report){
        int x =  videoService.updateReportExamine(report);
        if(x > 0)return ResultVO.success();
        return ResultVO.error();

    }

    /*查询我的作品*/
    @GetMapping("/queryAllMyShortVideoList")
    public ResultVO queryAllMyShortVideoList(@RequestParam Integer userId){
       List<ShortVideoDto> shortVideoDtos =   videoService.queryAllMyShortVideoList(userId);
       return  ResultVO.success(shortVideoDtos);
    }

    @PostMapping("/addVideo")
    public ResultVO addVideo(@RequestBody ShortVideo shortVideo){
        int i =  videoService.addVideo(shortVideo);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }
    /**
     * 短视频点赞
     * likeTypeId 表示对短视频 评论 回复的哪一种类型
     */
    @GetMapping("/like")
    public ResultVO like(@RequestParam Integer userId,@RequestParam String likeTypeId){
        Map<String,Object> map  =  videoService.like(userId,likeTypeId);
        return ResultVO.success(map);
    }

    /*查询短视频的所有评论和回复*/
    @GetMapping("/selectAllShortVideoTalkeAndReply")
    public ResultVO selectAllShortVideoTalkeAndReply(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                                     @RequestParam(required = false,defaultValue = "6")Integer pageSize,
                                                     ShortVideo shortVideo){
        Page<ShortVideoTalkeVo> videoTalkePage = videoService.selectAllShortVideoTalkeAndReply(pageNum,pageSize,shortVideo);
        return ResultVO.success(videoTalkePage);
    }

    /*添加短视频的评论*/
    @PostMapping("/addShortVideoTalke")
    public ResultVO addShortVideoTalke(@RequestBody ShortVideoTalke shortVideoTalke){
        int i = videoService.addShortVideoTalke(shortVideoTalke);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }
    /*添加短视频的回复*/
    @PostMapping("/addShortVideoReply")
    public ResultVO addShortVideoReply(@RequestBody ShortVideoReply shortVideoReply){
        int i = videoService.addShortVideoReply(shortVideoReply);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }

    /* 查询所有的影视评论和回复*/
    @GetMapping("/selectAllVideoTalkeAndReply")
    public ResultVO selectAllVideoTalkeAndReply(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                                @RequestParam(required = false,defaultValue = "6")Integer pageSize,
                                                Video video){
        Page<VideoTalkeVo> videoTalkePage = videoService.selectAllVideoTalkeAndReply(pageNum,pageSize,video);
        return ResultVO.success(videoTalkePage);
    }

    /*影视评论*/
    @PostMapping("addTalke")
    public ResultVO addTalke(@RequestBody VideoTalke videoTalke){
        int i =  videoService.addTalke(videoTalke);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }

    /*影视回复*/
    @PostMapping("addReply")
    public ResultVO addReply(@RequestBody VideoReply reply){
        int i =  videoService.addReply(reply);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }

    /*关注接口*/
    @GetMapping("/guanzhu")
    public ResultVO guanzhu(Integer guanZhuUserId,Integer beiGuanZhuUserId){
        HashMap<String,Object> map =  videoService.guanzhu(guanZhuUserId,beiGuanZhuUserId);
        return ResultVO.success(map);
    }

    @RequestMapping("queryMyShouCang")
    public ResultVO queryMyShouCang(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                    @RequestParam(required = false,defaultValue = "6")Integer pageSize,Integer userId){
        Page<Video> videoPage =  videoService.queryMyShouCang(pageNum,pageSize,userId);
        return ResultVO.success(videoPage);

    }

    @RequestMapping("addShouCang")
    public ResultVO addShouCang(Integer userId,String videoId){
        int i =  videoService.addShouCang(userId,videoId);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }
    /*查询该视频是否已经被收藏*/
    @RequestMapping("queryShouCangByUserIdAndVideoId")
    public ResultVO queryShouCangByUserIdAndVideoId(Integer userId,String videoId){
        int i = videoService.queryShouCangByUserIdAndVideoId(userId,videoId);
        if(i >0)return ResultVO.error();
        return ResultVO.success();
    }

    /*查询所有影视数量（包含综艺，动漫，电视剧）*/
    @GetMapping("/getAllVideoCount")
    public ResultVO getAllVideoCount(){
        long count = videoService.getAllVideoCount();
        return ResultVO.success(count);
    }
    /*查询所有短视频数量*/
    @GetMapping("/getAllShortVideoCount")
    public ResultVO getAllShortVideoCount(){
        long count = videoService.getAllShortVideoCount();
        return ResultVO.success(count);
    }

    @PostMapping("/updateVideoCover")
    public ResultVO updateVideoCover(String videoCover,String videoId){
        int i =  videoService.updateVideoCover(videoCover,videoId);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }

    /*修改影视数据*/
    @PutMapping("/updateVideo")
    public ResultVO updateVideo(@RequestBody Video video){
        int i =  videoService.updateVideo(video);
        if(i>0)return ResultVO.success();
        return ResultVO.error();
    }

    /*更改选集*/
    @PutMapping("/updateVideoChild")
    public ResultVO updateVideoChild(@RequestBody VideoChildren videoChildren){
        int i =  videoService.updateVideoChild(videoChildren);
        if(i>0)return ResultVO.success();
        return ResultVO.error();
    }

    /*添加搜索记录信息*/
    @PostMapping("/addHistory")
    public ResultVO addHistory(@RequestBody History history){
        int i = videoService.addHistory(history);
        if(i>0)return ResultVO.success();
        return ResultVO.error();
    }
    /*查询记录是否已经存在*/
    @GetMapping("/queryHistoryByHistoryName")
    public ResultVO queryHistoryByHistoryName(History history){
        String historyName =  videoService.queryHistoryByHistoryName(history);
        if(historyName == null)return ResultVO.success();
        return ResultVO.error();
    }
    /*查询所有搜索记录*/
    @GetMapping("/queryAllHistoryName")
    public ResultVO queryAllHistoryName(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                        @RequestParam(required = false,defaultValue = "8") Integer pageSize){
        Page<String> historyList =  videoService.queryAllHistoryName(pageNum,pageSize);
        return ResultVO.success(historyList);
    }
    /*删除单个搜索记录*/
    @DeleteMapping("/deleteHistory")
    public ResultVO deleteHistory(String historyName){
        int i =  videoService.deleteHistory(historyName);
        if(i>0)return ResultVO.success();
        return ResultVO.error();
    }


}
