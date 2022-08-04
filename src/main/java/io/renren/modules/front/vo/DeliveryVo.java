package io.renren.modules.front.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DeliveryVo {
    @NotBlank(message = "案件类型不能为空")
    private Integer caseType; //案件类型 0 正社员 1 IT案件 默认0
    @Size(min = 1, message = "案件id不能为空")
    private List<Long> caseIds;
}
