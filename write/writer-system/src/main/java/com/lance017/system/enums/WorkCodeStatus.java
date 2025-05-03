package com.lance017.system.enums;

import lombok.Getter;


@Getter
public enum WorkCodeStatus {

    NO_USED(1, "未使用"),

    USED(2, "已使用"),

    EXPIRED(0, "废弃");

    private final int code;

    private final String info;

    WorkCodeStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

}
