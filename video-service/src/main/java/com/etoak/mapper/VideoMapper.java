package com.etoak.mapper;

import com.etoak.dto.ReportDto;
import com.etoak.dto.ShortVideoDto;
import com.etoak.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    List<Video> getVideos(Video video);
    List<ShortVideoDto> queryAllShortVideo(ShortVideo shortVideo);

    int addReport(ReportVo reportVo);

    int addReportImgs(ReportVo reportVo);

    List<ReportDto> queryAllReportInfoByStatus(@Param("userId") Integer userId, @Param("reportStatus") Integer reportStatus);

    List<ReportDto> queryAllReportInfoList(Report report);

    int updateReportExamine(Report report);

    List<ShortVideoDto> queryAllMyShortVideoList(Integer userId);

    int addVideo(ShortVideo shortVideo);

    List<ShortVideoTalkeVo> selectAllShortVideoTalkeAndReply(ShortVideo shortVideo);

    int addShortVideoTalke(ShortVideoTalke shortVideoTalke);

    int addShortVideoReply(ShortVideoReply shortVideoReply);

    List<VideoTalkeVo> selectAllVideoTalkeAndReply(Video video);

    int addTalke(VideoTalke videoTalke);

    int addReply(VideoReply reply);

    void updateShortVideoById(@Param("shortVideoId") Integer shortVideoId, @Param("videoStatus") int i);

    void updateVideoById(@Param("videoId") String videoId, @Param("videoStatus") int i);

    ReportDto queryReportResultByid(Integer reportId);
    List<Video> queryMyShouCang(Integer userId);

    int addShouCang(Integer userId, String videoId);

    int queryShouCangByUserIdAndVideoId(Integer userId, String videoId);

    //添加电影接口数据
    void addVideoPlus(@Param("videoList")List<Video> videoList);

    void addVideoChildrens(@Param("id") String id, @Param("urls") List<String> urls);

    int queryAllTalkeAndReplyCount(String videoId);
    long getAllVideoCount();
    long getAllShortVideoCount();

    int updateVideoCover(@Param("videoCover") String videoCover,@Param("videoId") String videoId);

    int updateVideo(Video video);

    int videoChildren(VideoChildren videoChildren);

    int addHistory(History history);

    String queryHistoryByHistoryName(History history);

    List<String> queryAllHistoryName();

    int deleteHistory(@Param("historyName") String historyName);
}
