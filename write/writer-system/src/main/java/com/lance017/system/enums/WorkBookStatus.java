package com.lance017.system.enums;

import lombok.Getter;


@Getter
public enum WorkBookStatus {

    NORMAL(1, "正常"),

    DELETED(0, "删除");

    private final int code;

    private final String info;

    WorkBookStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

}
