package com.etoak.dto;

import com.etoak.entity.Report;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class ReportDto extends Report {
    private String videoTitle;
    private String reportUserName;
    private List<String> reportImgs;
    private String videoPath;
    private String videoCover;
}
