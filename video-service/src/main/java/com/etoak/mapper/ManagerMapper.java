package com.etoak.mapper;


import com.etoak.entity.VideoTalkeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ManagerMapper {
    void updateVisit();

    int getAllVisitCount();

    List<Map<String, Object>> queryFiveDayPlayRank();

    Long queryCountOfVideoById(@Param("videoId") String videoId, @Param("playTime") String beforeString);

    List<VideoTalkeVo> selectAllVideoTalkeAndReply(@Param("videoTitle") String videoTitle, @Param("userName") String userName);

    int updateShortVideoStatus(@Param("id") Integer id, @Param("videoStatus") Integer videoStatus);

    int examineResult(@Param("id") Integer id, @Param("status") Integer status);
}
