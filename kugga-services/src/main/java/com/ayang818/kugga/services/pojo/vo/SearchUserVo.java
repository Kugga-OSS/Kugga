package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/25 17:04
 **/
@Data
@Builder
public class SearchUserVo implements Vo {
    public Integer state;
    public List<UserVo> resList;
}
