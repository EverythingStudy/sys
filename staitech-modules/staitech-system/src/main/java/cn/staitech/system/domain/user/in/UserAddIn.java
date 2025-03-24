package cn.staitech.system.domain.user.in;

import cn.staitech.common.core.annotation.Excel;
import cn.staitech.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserAddIn extends BaseEntity {


    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构id")
    private Long organizationId;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户名称")
    @Excel(name = "用户名称")
    @NotNull(message = "{UserAddIn.userName.notNull}")
    @Length(max = 500,message = "{UserAddIn.userName.length}")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "姓名")
    @Excel(name = "姓名")
    @NotNull(message = "{UserAddIn.nickName.notNull}")
    @Length(max = 500,message = "{UserAddIn.userName.length}")
    private String nickName;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "部门名称")
    @NotNull(message = "{UserAddIn.dept.notNull}")
    @Length(max=500,message = "{UserAddIn.dept.length}")
    private String dept;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    @Excel(name = "用户邮箱")
    @Email(message = "{UserAddIn.email.email}")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Excel(name = "手机号码")
    @NotNull(message = "{UserAddIn.phonenumber.notNull}")
    @Length(min = 0,max = 500,message = "{UserAddIn.phonenumber.length}")
    private String phonenumber;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "用户性别", notes = "0=男,1=女")
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    @NotNull(message = "{UserAddIn.sex.notNull}")
    private String sex;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull(message = "{UserAddIn.roleIds.notNull}")
    @ApiModelProperty(value = "角色组")
    private Long[] roleIds;


}
