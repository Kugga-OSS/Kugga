package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/28 14:57
 **/
@Data
@Builder
public class UploadAvatarVo implements Vo {
    Integer state;
    String message;
    String url;
}
