package com.neticon.neticon.common.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class BaseResponse<T> implements Serializable {
    protected boolean success;
    protected Integer errorCode;
    protected String errorMsg;
    protected T result;

    private BaseResponse(Builder<T> builder) {
        this.success = builder.success;
        this.errorCode = builder.errorCode;
        this.errorMsg = builder.errorMsg;
        this.result = builder.result;
    }

    public static Builder newSuccResponse() {
        return new Builder().success(true);
    }

    public static Builder newFailResponse() {
        return new Builder().success(false);
    }

    public static final class Builder<T> {
        private boolean success = false;
        private Integer errorCode;
        private String errorMsg;
        private T result;

        private Builder() {
        }

        public BaseResponse build() {
            return new BaseResponse(this);
        }

        public Builder success(boolean success) {
            this.success = success;
            this.errorCode = 100;
            return this;
        }

        public Builder errorCode(Integer errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder errorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public Builder result(T result) {
            this.result = result;
            return this;
        }
    }
}
