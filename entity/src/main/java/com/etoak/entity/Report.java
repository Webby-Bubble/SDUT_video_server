package com.etoak.entity;

import lombok.Data;

@Data
public class Report {
    private int id;
    private Integer shortVideoId;
    private String videoId;
    private Integer userId;
    private String reportContent;
    private Integer reportStatus;
    private Integer examineStatus;
    private String examineContent;
    private String createTime;
    private String endTime;
}
