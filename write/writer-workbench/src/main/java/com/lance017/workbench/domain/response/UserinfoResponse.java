package com.lance017.workbench.domain.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserinfoResponse {

    private String username;

    private String nickname;

    private String avatar;

    private Integer sex;

//    private List<Map<String, Object>> words;
    private Long words;

    private String rigisterGiftWords;

}
