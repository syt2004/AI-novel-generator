package com.lance017.system.enums;

import lombok.Getter;


@Getter
public enum WorkCodeType {

    PERMANENT(1, "永久有效"),

    MONTH_ONE(2, "1个月"),

    ;

    private final int code;

    private final String info;

    WorkCodeType(int code, String info) {
        this.code = code;
        this.info = info;
    }

}
