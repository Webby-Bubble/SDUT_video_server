package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortVideo {
    private int id;
    private int userId;
    private String videoPath;
    private String videoContent;
    private String videoCover;
    private String publisTime;
    private Integer videoStatus;
}
