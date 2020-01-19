package com.ayang818.kugga.starter.controller;

import com.ayang818.kugga.starter.enums.Result;
import com.ayang818.kugga.starter.pojo.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨丰畅
 * @description 用户操作的相关接口
 * @date 2020/1/18 11:52
 **/
@Api(tags = "用户接口", description = "用户操作的相关接口")
@RestController
public class UserController {

    @ApiOperation("用户注册接口")
    @RequestMapping(value = "/api/user/register", method = RequestMethod.POST)
    public ResultDto register() {
        return Result.ok(null);
    }

    @ApiOperation("用户登录接口")
    @RequestMapping(value = "/api/user/login", method = RequestMethod.GET)
    public ResultDto login() {
        return Result.ok(null);
    }
}
