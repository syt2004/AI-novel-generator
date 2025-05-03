package com.lance017.workbench.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class OpenAiData {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Choice> choices;

    @Data
    public class Choice {

        private Integer index;

        private String finish_reason;

        private Delta delta;

    }

    @Data
    public class Delta {
        private String content;
        private String role;
    }


}





