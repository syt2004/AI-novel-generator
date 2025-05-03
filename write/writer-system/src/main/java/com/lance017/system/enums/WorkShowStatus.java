package com.lance017.system.enums;

import lombok.Getter;


@Getter
public enum WorkShowStatus {

    YES(1, "显示"),

    NO(0, "不显示");

    private final int code;

    private final String info;

    WorkShowStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

}
