package com.lance017.system.enums;

import lombok.Getter;


@Getter
public enum WorkDescType {

    ID(1, "id"),

    STYLE(2, "写作风格"),

    REQUIRES(3, "写作要求"),

    BREAK_REQUIRES(4, "拆书要求"),

    POLISH_REQUIRES(5, "扩写要求"),
    ;

    private final int code;

    private final String info;

    WorkDescType(int code, String info) {
        this.code = code;
        this.info = info;
    }

}
