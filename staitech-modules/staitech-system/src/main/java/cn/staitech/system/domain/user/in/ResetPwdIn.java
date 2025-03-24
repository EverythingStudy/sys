package cn.staitech.system.domain.user.in;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ResetPwdIn {
    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "{ChangeStatusIn.userId.isnull}")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
