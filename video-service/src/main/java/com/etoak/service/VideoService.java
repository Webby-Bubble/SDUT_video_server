package com.etoak.service;


import com.etoak.dto.ReportDto;
import com.etoak.dto.ShortVideoDto;
import com.etoak.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface VideoService {
    Page<Video> getVideos(Integer pageNum, Integer pageSize, Video video);

    Page<ShortVideoDto> queryAllShortVideo(Integer pageNum, Integer pageSize, ShortVideo shortVideo);

    int addVideoReport(ReportVo reportVo);

    List<ReportDto> queryAllReportInfoByStatus(Integer userId, Integer reportStatus);

    Page<ReportDto> queryAllReportInfoList(Integer pageNum, Integer pageSize, Report report);

    int updateReportExamine(ReportVo report);

    List<ShortVideoDto> queryAllMyShortVideoList(Integer userId);

    int addVideo(ShortVideo shortVideo);

    Map<String, Object> like(Integer userId, String likeTypeId);

    Page<ShortVideoTalkeVo> selectAllShortVideoTalkeAndReply(Integer pageNum, Integer pageSize, ShortVideo shortVideo);

    int addShortVideoTalke(ShortVideoTalke shortVideoTalke);

    int addShortVideoReply(ShortVideoReply shortVideoReply);

    Page<VideoTalkeVo> selectAllVideoTalkeAndReply(Integer pageNum, Integer pageSize, Video video);

    int addTalke(VideoTalke videoTalke);

    int addReply(VideoReply reply);

    HashMap<String, Object> guanzhu(Integer guanZhuUserId, Integer beiGuanZhuUserId);


    ReportDto queryReportResultByid(Integer reportId);
    Page<Video> queryMyShouCang(Integer pageNum,Integer pageSize,Integer userId);

    int addShouCang(Integer userId, String videoId);

    int queryShouCangByUserIdAndVideoId(Integer userId, String videoId);

    int queryAllTalkeAndReplyCount(String videoId);

    long getAllVideoCount();
    long getAllShortVideoCount();

    int updateVideoCover(String videoCover,String videoId);

    int updateVideo(Video video);

    int updateVideoChild(VideoChildren videoChildren);

    int addHistory(History history);

    String queryHistoryByHistoryName(History history);

    Page<String> queryAllHistoryName(Integer pageNum,Integer pageSize);

    int deleteHistory(String historyName);
}
