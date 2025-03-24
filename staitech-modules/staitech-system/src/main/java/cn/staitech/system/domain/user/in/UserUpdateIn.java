package cn.staitech.system.domain.user.in;

import cn.staitech.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserUpdateIn {
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户id")
    @NotNull(message = "{ChangeStatusIn.userId.isnull}")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    @NotNull(message = "{UserUpdateIn.nickName.notNull}")
    @Length(max = 500,message = "{UserAddIn.userName.length}")
    private String nickName;

    @ApiModelProperty(value = "用户性别", notes = "0=男,1=女")
    private String sex;

    @ApiModelProperty(value = "手机号码")
    @Size(min = 0, max = 500, message = "{UserUpdateIn.phonenumber.length}")
    @Length(min = 0,max = 500,message = "{UserUpdateIn.phonenumber.length}")
    private String phonenumber;

    @ApiModelProperty(value = "机构id")
    @NotNull(message = "{UserUpdateIn.organizationId.notNull}")
    private Long organizationId;

    @ApiModelProperty(value = "部门名称")
    @Size(max = 500, message = "{UserUpdateIn.dept.length}")
    @NotNull(message = "{UserAddIn.dept.notNull}")
    @Length(max=500,message = "{UserUpdateIn.dept.length}")
    private String dept;

    @ApiModelProperty(value = "用户邮箱")
    @Excel(name = "用户邮箱")
    @Email(message = "{UserAddIn.email.email}")
    private String email;

    @ApiModelProperty(value = "系统角色id")
    private Long roleId;

    @NotNull(message = "{UserAddIn.roleIds.notNull}")
    @ApiModelProperty(value = "角色组")
    private Long[] roleIds;
}
