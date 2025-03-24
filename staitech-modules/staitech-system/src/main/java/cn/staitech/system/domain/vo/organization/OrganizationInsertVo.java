package cn.staitech.system.domain.vo.organization;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author gjt.
 */
@Data
public class OrganizationInsertVo {

    @NotNull(message = "{OrganizationInsertVo.organizationName.notNull}")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z\\d]{1,20}$",message = "{OrganizationInsertVo.organizationName.pattern}")
    @ApiModelProperty(value = "机构名称", required = true)
    @Size(max = 500,message = "organizationName overlength")
    private String organizationName;


    @NotNull(message = "{OrganizationInsertVo.authorizationMemberLimit.notNull}")
    @ApiModelProperty(value = "授权用户人数")
    private Long authorizationMemberLimit;


    @NotNull(message = "{OrganizationInsertVo.authorizationTime.notNull}")
    @ApiModelProperty(value = "授权时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String authorizationTime;

    @NotNull(message = "OrganizationInsertVo.expirationTime.notNull")
    @ApiModelProperty(value = "到期时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String expirationTime;

}
