package cn.staitech.system.domain.user.in;

import cn.staitech.common.core.domain.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class UserListQeuryIn extends PageRequest {
    /** 用户账号 */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String nickName;

    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    @ApiModelProperty(value = "机构名称")
    private String organizationName;

    @ApiModelProperty(value = "系统角色id")
    private Long roleId;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "起始创建时间")
    private Date beginTime;

    @ApiModelProperty(value = "终止创建时间")
    private Date endTime;

    @ApiModelProperty(value = "时间范围")
    private Map<String,Date> createTime;

    @ApiModelProperty(value = "系统角色id组")
    private Long[] roleIds;
}
