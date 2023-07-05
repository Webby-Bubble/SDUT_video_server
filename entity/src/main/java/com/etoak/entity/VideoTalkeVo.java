package com.etoak.entity;

import lombok.Data;

import java.util.List;

@Data
public class VideoTalkeVo extends VideoTalke{
    private String userName;
    private String userPic;
    private List<VideoReply> videoReplyList;
    private int videoTalkeAndReplyCount;//视频的评论和回复总数量

    private String videoTitle;

}
