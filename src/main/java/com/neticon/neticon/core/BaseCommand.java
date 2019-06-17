package com.neticon.neticon.core;

import com.neticon.neticon.common.domain.response.BaseResponse;
import com.neticon.neticon.common.enums.NetIconErrorCodeEnum;

public abstract class BaseCommand {
    protected BaseResponse getSuccessResponse(Object res) {
        return BaseResponse
                .newSuccResponse()
                .result(res)
                .build();
    }

    protected BaseResponse getFailResponse(NetIconErrorCodeEnum errorCode) {
        return BaseResponse
                .newFailResponse()
                .errorCode(errorCode.getCode())
                .errorMsg(errorCode.getMsg())
                .build();
    }
}
