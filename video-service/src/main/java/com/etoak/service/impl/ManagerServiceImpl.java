package com.etoak.service.impl;


import com.etoak.entity.Page;
import com.etoak.entity.VideoTalkeVo;
import com.etoak.mapper.ManagerMapper;
import com.etoak.service.ManagerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Resource
    ManagerMapper managerMapper;

    @Override
    public int getAllVisitCount() {
        return managerMapper.getAllVisitCount();
    }

    @Override
    public Map<String, Object> queryFiveDayPlayRank() {
        //返回所有封装的数据
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> mapList =  managerMapper.queryFiveDayPlayRank();
        //存放近五天排行前三的影视名城
        List<String> videoTitles = new ArrayList<>();
        for(Map<String, Object> mm : mapList){
            videoTitles.add(mm.get("videoTitle").toString());
        }
        map.put("videoTitles",videoTitles);
        //封装最近五天的日期（包含今天）
        List<String> timeList = new ArrayList<>();

        long nowTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //根据日期和视频id获取近五天每天的播放量数据
        for(int x = 0;x <= 4;x++){
            long beforeTime = nowTime - (86400000 * x);
            String beforeString = sdf.format(beforeTime);
            timeList.add(beforeString);
            //用来存放排行榜前三的每条数据每天的播放量
            List<Long> counts = new ArrayList<>();
            for(Map<String, Object> m :mapList ){
                Long count =  managerMapper.queryCountOfVideoById(m.get("videoId").toString(),beforeString);
                count = count == null? 0:count;
                counts.add(count);
            }
            map.put("before"+x,counts);
        }
        map.put("fiveTime",timeList);
        return map;
    }

    @Override
    public Page<VideoTalkeVo> selectAllVideoTalkeAndReply(String videoTitle, String userName, Integer pageNum, Integer pageSize) {

        //使用mybatis插件帮助我们分页
        PageHelper.startPage(pageNum,pageSize);
        //查询列表数据
        List<VideoTalkeVo> voList =  managerMapper.selectAllVideoTalkeAndReply(videoTitle,userName);
        //使用PageInfo帮助我们计算分页数据 如：pageNum pageSize total
        PageInfo<VideoTalkeVo> pageInfo = new PageInfo<>(voList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                voList,pageInfo.getPages(),pageInfo.getTotal(),pageInfo.getPrePage(),pageInfo.getNextPage());

    }

    @Override
    public int updateShortVideoStatus(Integer id, Integer videoStatus) {
        return managerMapper.updateShortVideoStatus(id,videoStatus);
    }

    @Override
    public int examineResult(Integer id, Integer status) {
        return managerMapper.examineResult(id,status);
    }
}
