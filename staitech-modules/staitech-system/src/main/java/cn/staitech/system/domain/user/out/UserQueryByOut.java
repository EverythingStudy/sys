package cn.staitech.system.domain.user.out;

import cn.staitech.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gjt.
 * @data 2023/6/2 15:31
 */
@Data
public class UserQueryByOut {
    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色")
    private Long userId;

    @ApiModelProperty("名称")
    private String userName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("性别")
    private Long sex;

    @ApiModelProperty("部门名称")
    private String dept;

    @ApiModelProperty("机构id")
    private Long organizationId;

    @ApiModelProperty("机构名称")
    private String organizationName;
}
