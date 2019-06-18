package com.neticon.neticon.common.request;

import com.neticon.neticon.common.vo.UserInfoVo;
import lombok.Data;

@Data
public class BaseRequest {
    private UserInfoVo userInfo;
}
