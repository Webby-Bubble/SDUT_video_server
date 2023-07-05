package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private String id;
    private String videoTitle;
    private String videoCover;
    private String videoPath;
    private String videoTypes;
    private String director;
    private String stars;
    private String publishTime;
    private Integer videoKind;
    private String videoContent;
    private List<VideoChildren> videoChildrenList;
    private String zuixin;//点击最新进行排序字段
    private String pingFen;//评分
    private Double doubanScore;//评分
    private int videoStatus;//上下架
    private Integer role;//用户权限

}
