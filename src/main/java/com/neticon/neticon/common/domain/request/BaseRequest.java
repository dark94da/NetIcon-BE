package com.neticon.neticon.common.domain.request;

import com.neticon.neticon.common.domain.vo.UserInfoVo;
import lombok.Data;

@Data
public class BaseRequest {
    private UserInfoVo userInfo;
}
