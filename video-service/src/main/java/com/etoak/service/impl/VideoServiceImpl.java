package com.etoak.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.etoak.dto.ReportDto;
import com.etoak.dto.ShortVideoDto;
import com.etoak.entity.*;
import com.etoak.mapper.VideoMapper;
import com.etoak.mqConfig.RabbitMQConfig;
import com.etoak.service.VideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {
    @Resource
    VideoMapper videoMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Page<Video> getVideos(Integer pageNum, Integer pageSize, Video video) {
        //使用mybatis插件帮助我们分页
        PageHelper.startPage(pageNum,pageSize);
        //查询列表数据
        List<Video> voList =  videoMapper.getVideos(video);
        voList.forEach(x->{
            String[] directors = x.getDirector().split(",");
            StringBuffer buffer   = new StringBuffer();

            if(directors.length >= 4){
                for(int y = 0;y <= 3;y++){
                    buffer.append(directors[y]).append(",");
                }
                x.setDirector(buffer.toString().substring(0,buffer.toString().length()-1));
            }

        });
        //使用PageInfo帮助我们计算分页数据 如：pageNum pageSize total
        PageInfo<Video> pageInfo = new PageInfo<>(voList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                voList,pageInfo.getPages(),pageInfo.getTotal(),pageInfo.getPrePage(),pageInfo.getNextPage());
    }

    public Page<ShortVideoDto> queryAllShortVideo(Integer pageNum, Integer pageSize, ShortVideo shortVideo) {
        //使用mybatis插件帮助我们分页
        PageHelper.startPage(pageNum,pageSize);
        //查询列表数据
        List<ShortVideoDto> voList =  videoMapper.queryAllShortVideo(shortVideo);
        //封装每条数据的点赞数据
        for(ShortVideoDto sv : voList){
            if(redisTemplate.opsForSet().isMember("shortVideo"+sv.getId(),shortVideo.getUserId() + "")){
                sv.setConfirmLike(true);
            }
            if(redisTemplate.opsForSet().isMember("guanzhu"+shortVideo.getUserId(),sv.getUserId() + "")){
                sv.setGuanZhu(true);
            }
            sv.setLikeCount(redisTemplate.opsForSet().size("shortVideo"+sv.getId()));
        }

        //使用PageInfo帮助我们计算分页数据 如：pageNum pageSize total
        PageInfo<ShortVideoDto> pageInfo = new PageInfo<>(voList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                voList,pageInfo.getPages(),pageInfo.getTotal(),pageInfo.getPrePage(),pageInfo.getNextPage());
    }
    @Transactional
    @Override
    public int addVideoReport(ReportVo reportVo) {
        int x =  videoMapper.addReport(reportVo);
        int i =  videoMapper.addReportImgs(reportVo);
        if(x > 0 && i > 0)return 1;
        return 0;
    }

    @Override
    public List<ReportDto> queryAllReportInfoByStatus(Integer userId, Integer reportStatus) {
        return videoMapper.queryAllReportInfoByStatus(userId,reportStatus);
    }

    @Override
    public Page<ReportDto> queryAllReportInfoList(Integer pageNum, Integer pageSize, Report report) {
        PageHelper.startPage(pageNum,pageSize);

        List<ReportDto> reportDtoList = videoMapper.queryAllReportInfoList(report);
        PageInfo<ReportDto> pageInfo = new PageInfo<>(reportDtoList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                reportDtoList,pageInfo.getPages(),pageInfo.getTotal(),pageInfo.getPrePage(),pageInfo.getNextPage());
    }

    /* 修改举报表的审核信息 给用户发送邮件 */
    @Override
    public int updateReportExamine(ReportVo report) {
        int i = videoMapper.updateReportExamine(report);

        //举报成功之后更改视频的状态为违规
        if(report.getExamineStatus() == 1){
            if(report.getShortVideoId() != null){
                //修改短视频状态为违规
                videoMapper.updateShortVideoById(report.getShortVideoId(),3);
            }else {
                //修改影视状态 下架
                videoMapper.updateVideoById(report.getVideoId(),0);
            }

        }


            String resultStr =  report.getExamineStatus() == 1?"举报成功":"举报失败";
        /* 审核成功 向消息队列发送邮件信息*/
        if(i > 0){
            Email email = new Email("审核结果", report.getEmail(),
                    resultStr + "\n\n尊敬的 @" + report.getReportUserName() + " 用户，您好！\n" +
                           "您举报的  “" + report.getVideoTitle() + " “"  +report.getExamineContent());

            String msg = JSONObject.toJSONString(email);

            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.KEY,
                    msg);

        }
        return i;
    }

    @Override
    public List<ShortVideoDto> queryAllMyShortVideoList(Integer userId) {

        //查询列表数据
        List<ShortVideoDto> voList =  videoMapper.queryAllMyShortVideoList(userId);
        //封装每条数据的点赞数据
        for(ShortVideoDto sv : voList){
            if(redisTemplate.opsForSet().isMember("shortVideo"+sv.getId(),userId + "")){
                sv.setConfirmLike(true);
            }
            sv.setLikeCount(redisTemplate.opsForSet().size("shortVideo"+sv.getId()));
        }


        return voList;
    }

    @Override
    public int addVideo(ShortVideo shortVideo) {
        return videoMapper.addVideo(shortVideo);
    }

    @Override
    public Map<String,Object> like(Integer userId, String likeTypeId) {
        Map<String,Object> map = new HashMap<>();
        if(redisTemplate.opsForSet().isMember(likeTypeId,userId + "")){
            redisTemplate.opsForSet().remove(likeTypeId,userId + "");
            map.put("confirmLike",false);
            map.put("likeCount",this.likeCount(likeTypeId));
            return map;
        }else {
            redisTemplate.opsForSet().add(likeTypeId,userId.toString());
            map.put("confirmLike",true);
            map.put("likeCount",this.likeCount(likeTypeId));
            return map;
        }
    }
    public long likeCount(String likeTypeId){
        return redisTemplate.opsForSet().size(likeTypeId);
    }

    @Override
    public Page<ShortVideoTalkeVo> selectAllShortVideoTalkeAndReply(Integer pageNum, Integer pageSize, ShortVideo shortVideo) {
        //使用mybatis插件帮助我们分页
        PageHelper.startPage(pageNum,pageSize);
        //查询列表数据
        List<ShortVideoTalkeVo> voList =  videoMapper.selectAllShortVideoTalkeAndReply(shortVideo);


        for(ShortVideoTalkeVo tv : voList){
            for(ShortVideoReply rv :tv.getShortVideoReplyList()){
                if(redisTemplate.opsForSet().isMember("shortVideoReply"+rv.getId(),shortVideo.getUserId()+"")){
                    rv.setConfirmLike(true);

                }
                rv.setLikeCount(this.likeCount( "shortVideoReply"+rv.getId()));
            }
            if(redisTemplate.opsForSet().isMember("shortVideoTalke"+tv.getId(),shortVideo.getUserId()+"")){
                tv.setConfirmLike(true);
            }
            tv.setLikeCount(this.likeCount("shortVideoTalke"+tv.getId()));
        }

        //使用PageInfo帮助我们计算分页数据 如：pageNum pageSize total
        PageInfo<ShortVideoTalkeVo> pageInfo = new PageInfo<>(voList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                voList,pageInfo.getPages(),pageInfo.getTotal(),pageInfo.getPrePage(),pageInfo.getNextPage());
    }
    @Override
    public int addShortVideoTalke(ShortVideoTalke shortVideoTalke) {
        return videoMapper.addShortVideoTalke(shortVideoTalke);
    }
    @Override
    public int addShortVideoReply(ShortVideoReply shortVideoReply) {
        return videoMapper.addShortVideoReply(shortVideoReply);
    }

    @Override
    public Page<VideoTalkeVo> selectAllVideoTalkeAndReply(Integer pageNum, Integer pageSize,Video video) {
        //使用mybatis插件帮助我们分页
        PageHelper.startPage(pageNum,pageSize);
        //查询列表数据
        List<VideoTalkeVo> voList =  videoMapper.selectAllVideoTalkeAndReply(video);
        //使用PageInfo帮助我们计算分页数据 如：pageNum pageSize total
        PageInfo<VideoTalkeVo> pageInfo = new PageInfo<>(voList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                voList,pageInfo.getPages(),pageInfo.getTotal(),pageInfo.getPrePage(),pageInfo.getNextPage());
    }

    @Override
    public int addTalke(VideoTalke videoTalke) {
        return videoMapper.addTalke(videoTalke);
    }

    @Override
    public int addReply(VideoReply reply) {
        return videoMapper.addReply(reply);
    }

    @Override
    public HashMap<String, Object> guanzhu(Integer guanZhuUserId, Integer beiGuanZhuUserId) {
        HashMap<String,Object> map = new HashMap<>();
        String guanZhu = "guanzhu" + guanZhuUserId;//点关注人的关注列表的key
        String fenSi = "fensi" + beiGuanZhuUserId;//点关注的人同时成为被关注的粉丝
        if(redisTemplate.opsForSet().isMember(guanZhu,beiGuanZhuUserId.toString())){
            //已经点过关注将取消关注同时取消被关注人的粉丝
            redisTemplate.opsForSet().remove(guanZhu,beiGuanZhuUserId.toString());
            redisTemplate.opsForSet().remove(fenSi,guanZhuUserId.toString());
            map.put("isGuanZhu",false);
            return map;
        }else {
            //添加关注信息和被关注的粉丝信息
            redisTemplate.opsForSet().add(guanZhu,beiGuanZhuUserId.toString());
            redisTemplate.opsForSet().add(fenSi,guanZhuUserId.toString());
            map.put("isGuanZhu",true);
            return map;
        }
    }

    @Override
    public ReportDto queryReportResultByid(Integer reportId) {
        return videoMapper.queryReportResultByid(reportId);
    }

    @Override
    public Page<Video> queryMyShouCang(Integer pageNum,Integer pageSize,Integer userId) {
        //使用mybatis插件帮助我们分页
        PageHelper.startPage(pageNum,pageSize);
        //查询列表数据
        List<Video> voList =  videoMapper.queryMyShouCang(userId);
        //使用PageInfo帮助我们计算分页数据 如：pageNum pageSize total
        PageInfo<Video> pageInfo = new PageInfo<>(voList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                voList,pageInfo.getPages(),pageInfo.getTotal(),pageInfo.getPrePage(),pageInfo.getNextPage());

    }

    @Override
    public int addShouCang(Integer userId, String videoId) {
        return videoMapper.addShouCang(userId,videoId);
    }

    @Override
    public int queryShouCangByUserIdAndVideoId(Integer userId, String videoId) {
        return videoMapper.queryShouCangByUserIdAndVideoId(userId,videoId);
    }

    @Override
    public int queryAllTalkeAndReplyCount(String videoId) {
        return videoMapper.queryAllTalkeAndReplyCount(videoId);
    }

    @Override
    public long getAllVideoCount() {
        return videoMapper.getAllVideoCount();
    }

    @Override
    public long getAllShortVideoCount() {
        return videoMapper.getAllShortVideoCount();
    }

    @Override
    public int updateVideoCover(String videoCover,String videoId) {
        return videoMapper.updateVideoCover(videoCover,videoId);
    }

    @Override
    public int updateVideo(Video video) {
        return videoMapper.updateVideo(video);
    }

    @Override
    public int updateVideoChild(VideoChildren videoChildren) {
        return videoMapper.videoChildren(videoChildren);
    }

    @Override
    public int addHistory(History history) {
        return videoMapper.addHistory(history);
    }

    @Override
    public String queryHistoryByHistoryName(History history) {
        return videoMapper.queryHistoryByHistoryName(history);
    }

    @Override
    public Page<String> queryAllHistoryName(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<String> historyList =  videoMapper.queryAllHistoryName();
        PageInfo<String> pageInfo = new PageInfo<>(historyList);
        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                historyList,pageInfo.getPages(),pageInfo.getTotal(),pageInfo.getPrePage(),pageInfo.getNextPage());
    }

    @Override
    public int deleteHistory(String historyName) {
        return videoMapper.deleteHistory(historyName);
    }
}
