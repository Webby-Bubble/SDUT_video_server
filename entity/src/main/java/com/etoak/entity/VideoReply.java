package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoReply {
    private int id;
    private int videoTalkeId;
    private int userId;
    private String replyContent;
    private String replyTime;
    private String rname;
    private String replyUserName;
    private String replyUserPic;
}
