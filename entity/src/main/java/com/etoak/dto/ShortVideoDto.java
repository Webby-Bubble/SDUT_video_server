package com.etoak.dto;

import com.etoak.entity.ShortVideo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortVideoDto extends ShortVideo {
    private String userName;
    private String userPic;
    private int talkeAndReplyCount;//评论总数
    private boolean confirmLike;//是否点赞
    private long likeCount;//点赞数量
    private int userId;//用户id用于查看点赞数据状态
    private boolean guanZhu;//是否关注
}
