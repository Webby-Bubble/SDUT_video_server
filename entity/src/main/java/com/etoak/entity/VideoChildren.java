package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * Author @冷月
 * Date 2023/4/10 19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoChildren {
    private int id;
    private String videoId;
    private String videoPath;
    private int videoAnthology;
}
