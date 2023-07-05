package com.etoak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    //当前页
    private Integer pageNum;
    //每页记录数
    private Integer pageSize;
    //用来封装列表数据
    private List<T> rows;
    //总页数
    private Integer pageCount;
    //总的记录数
    private long total;
    //上一页
    private Integer pre;

    //下一页
    private Integer next;



}
