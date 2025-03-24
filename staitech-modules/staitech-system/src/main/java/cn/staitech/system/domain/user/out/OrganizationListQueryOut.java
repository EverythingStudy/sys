package cn.staitech.system.domain.user.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationListQueryOut {
    @ApiModelProperty("机构id")
    private Long organizationId;
    @ApiModelProperty("机构名称")
    private String organizationName;
    @ApiModelProperty("机构是否可用0:有效；1:无效")
    private String state;
}
