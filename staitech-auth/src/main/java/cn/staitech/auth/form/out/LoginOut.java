package cn.staitech.auth.form.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginOut {
    @ApiModelProperty("token值")
    private String accessToken;
    @ApiModelProperty("超时时长")
    private Long expiresIn;
    @ApiModelProperty("账号登陆状态：0-首次登陆；1—非首次登录")
    private String loginState;
}
