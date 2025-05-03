package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PartSortRequest {

    @NotNull(message = "参数错误")
    private List<Long> ids;

}
