package cn.staitech.system.domain.user.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PassWordUpdateIn {
    @ApiModelProperty(value = "旧密码：真实字符串")
    @NotNull(message = "{PassWordUpdateIn.oldPassword.notNull}")
    private String oldPassword;
    @ApiModelProperty(value = "新密码：加密后的字符串")
    @NotNull(message = "{PassWordUpdateIn.newPassword.notNull}")
    private String newPassword;

}
