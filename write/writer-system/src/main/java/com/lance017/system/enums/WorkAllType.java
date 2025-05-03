package com.lance017.system.enums;

import lombok.Getter;


@Getter
public enum WorkAllType {

    ID(1, "id"),

    TEXT(2, "text");

    private final int code;

    private final String info;

    WorkAllType(int code, String info) {
        this.code = code;
        this.info = info;
    }

}
