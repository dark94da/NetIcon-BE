package com.neticon.neticon.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequest extends BaseRequest {
    private String nickname;
    private String identifier;
}
