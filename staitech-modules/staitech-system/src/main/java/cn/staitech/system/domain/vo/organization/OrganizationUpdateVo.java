package cn.staitech.system.domain.vo.organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author gjt.
 * @data 2023/5/25 8:30
 */
@Data
public class OrganizationUpdateVo extends OrganizationInsertVo {

    @NotNull(message = "{OrganizationUpdateStatusVo.organizationId.notNull}")
    @ApiModelProperty(value = "机构id", required = true)
    private Long organizationId;

    @ApiModelProperty(value = "状态", required = true)
    private Long status;
}
