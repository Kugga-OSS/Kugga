package com.ayang818.kugga.starter.enums;

import com.ayang818.kugga.services.pojo.vo.Vo;
import com.ayang818.kugga.starter.pojo.ResultDto;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/2/5 14:50
 **/
public class VoUtil {

    public static ResultDto getFailDefault() {
        return Result.defaultRes();
    }

    public static ResultDto getSuccessDefault() {
        return Result.defaultTrueRes();
    }

    public static ResultDto judge(Vo vo) {
        if (vo == null) {
            return getFailDefault();
        }
        if (vo.getState() == 1) {
            return Result.ok(vo);
        } else if (vo.getState() == 0) {
            return Result.authenticateFailed(vo);
        } else if (vo.getState() == 2){
            return Result.reject(vo);
        } else {
            return getFailDefault();
        }
    }
}
