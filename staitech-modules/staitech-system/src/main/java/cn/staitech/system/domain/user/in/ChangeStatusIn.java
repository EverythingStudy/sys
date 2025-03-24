package cn.staitech.system.domain.user.in;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ChangeStatusIn {
    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "{ChangeStatusIn.userId.isnull}")
    private Long userId;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    @NotNull(message = "{ChangeStatusIn.status.isnull}")
    private String status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
