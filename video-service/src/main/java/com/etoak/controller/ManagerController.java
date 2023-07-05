package com.etoak.controller;

import com.etoak.entity.Page;
import com.etoak.vo.ResultVO;
import com.etoak.entity.VideoTalkeVo;
import com.etoak.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @GetMapping("/getAllVisitCount")
    public ResultVO getAllVisitCount(){
        int count = managerService.getAllVisitCount();
        return ResultVO.success(count);
    }

    /*统计近五天的视频播放量排行前三的数据*/
    @GetMapping("/queryFiveDayPlayRank")
    public ResultVO queryFiveDayPlayRank(){
      Map<String, Object> topThree =  managerService.queryFiveDayPlayRank();
      return ResultVO.success(topThree);
    }

    @GetMapping("selectAllTalkeAndReply")
    public ResultVO selectAllTalkeAndReply(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false,defaultValue = "6") Integer pageSize, String videoTitle, String userName){
        Page<VideoTalkeVo> talkeVoPage =  managerService.selectAllVideoTalkeAndReply(videoTitle,userName,pageNum,pageSize);
        return ResultVO.success(talkeVoPage);
    }

    @GetMapping("/updateShortVideoStatus")
    public ResultVO updateShortVideoStatus(@RequestParam Integer id,@RequestParam Integer videoStatus){
       int i =   managerService.updateShortVideoStatus(id,videoStatus);
       if(i > 0)return ResultVO.success();
       return ResultVO.error();
    }

    /*视频发布审核结果 */
    @GetMapping("/examineResult")
    public ResultVO examineResult(@RequestParam Integer id,@RequestParam Integer status){
        int i = managerService.examineResult(id,status);
        if(i > 0)return ResultVO.success();
        return ResultVO.error();
    }
}
