package com.etoak.entity;

import lombok.Data;

import java.util.List;

@Data
public class ReportVo extends Report{
    private List<String> reportImgs;

    //邮件中拼接的内容
    private String email;
    private String reportUserName;
    private String videoTitle;
}
