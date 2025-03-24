package cn.staitech.system.domain.user.out;

import cn.staitech.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class UserListQeuryOut {
    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    private Long deptId;
    /** 用户编码 */
    @ApiModelProperty(value = "用户编码")
    private String userCode;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构id")
    private Long organizationId;
    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称")
    private String organizationName;

    /** 用户账号 */
    @ApiModelProperty(value = "用户名称")
    @Excel(name = "用户名称")
    private String userName;

    /** 用户昵称 */
    @ApiModelProperty(value = "姓名")
    @Excel(name = "姓名")
    private String nickName;


    /** 手机号码 */
    @ApiModelProperty(value = "手机号码")
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 用户性别 */
    @ApiModelProperty(value = "用户性别")
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;


    /** 帐号状态（0正常 1停用） */
    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;



}
