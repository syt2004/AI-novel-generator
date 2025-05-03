package com.lance017.common.enums;

/**
 * 用户状态
 *
 * @author writer
 */
public enum CustomUserStatus {
    NORMAL(1, "正常"),
    DISABLE(0, "停用");

    private final Integer code;
    private final String info;

    CustomUserStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
