package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoTalke {
    private int id;
    private String videoId;
    private int userId;
    private String talkeContent;
    private String publishTime;
}
