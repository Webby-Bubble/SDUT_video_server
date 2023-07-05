package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortVideoTalkeVo extends ShortVideoTalke {
    private String userName;
    private String userPic;
    private boolean confirmLike;//是否点赞
    private long likeCount;//点赞数量
    private List<ShortVideoReply> shortVideoReplyList;
}
