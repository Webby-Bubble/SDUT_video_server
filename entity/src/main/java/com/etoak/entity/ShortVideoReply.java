package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortVideoReply {
    private int id;
    private int shortVideoTalkeId;
    private int userId;
    private String shortReplyContent;
    private String replyTime;
    private String rname;
    private String shortReplyUserName;
    private String shortReplyUserPic;

    //用来封装回复的点赞和点赞量
    private boolean confirmLike;//是否点赞
    private long likeCount;//点赞数量
}
