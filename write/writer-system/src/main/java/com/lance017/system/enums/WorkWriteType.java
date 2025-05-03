package com.lance017.system.enums;

import lombok.Getter;


@Getter
public enum WorkWriteType {

    AI_WRITE(1, "AI_WRITE"),

    AI_POLISH(2, "AI_POLISH"),
    BBBB(3, "text"),
    AI_BREAK(4, "AI_BREAK");

    private final int code;

    private final String info;

    WorkWriteType(int code, String info) {
        this.code = code;
        this.info = info;
    }

}
