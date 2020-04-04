package com.ayang818.kugga.starter.controller;

import com.ayang818.kugga.services.pojo.vo.MsgListVo;
import com.ayang818.kugga.services.service.MsgService;
import com.ayang818.kugga.starter.enums.VoUtil;
import com.ayang818.kugga.starter.pojo.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/22 14:35
 **/
@RestController
@Api(tags = "消息接口", description = "消息相关的接口")
public class MsgController {

    @Autowired
    MsgService msgService;

    @ApiOperation("用户拉取消息接口")
    @RequestMapping(value = "/auth_api/msg", method = RequestMethod.GET)
    public ResultDto pullMsg(HttpServletRequest req, @RequestParam("otherUid") String otherUid) {

        Long ownerUid = (Long) req.getAttribute("uid");
        MsgListVo msgListVo = msgService.fetchMsg(ownerUid, Long.parseLong(otherUid));
        return VoUtil.judge(msgListVo);
    }
}
