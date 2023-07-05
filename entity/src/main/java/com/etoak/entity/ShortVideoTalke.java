package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortVideoTalke {
    private int id;
    private int shortVideoId;
    private int userId;
    private String shortTalkeContent;
    private String publishTime;

}
