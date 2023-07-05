package com.etoak.service;



import com.etoak.entity.Page;
import com.etoak.entity.VideoTalkeVo;

import java.util.Map;

public interface ManagerService {
    int getAllVisitCount();


    Map<String, Object> queryFiveDayPlayRank();
    Page<VideoTalkeVo> selectAllVideoTalkeAndReply(String videoTitle, String userName, Integer pageNum, Integer pageSize);

    int updateShortVideoStatus(Integer id, Integer videoStatus);

    int examineResult(Integer id, Integer status);
}
