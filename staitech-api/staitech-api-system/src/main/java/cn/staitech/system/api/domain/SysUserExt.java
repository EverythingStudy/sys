package cn.staitech.system.api.domain;

import cn.staitech.common.core.annotation.Excel;
import cn.staitech.common.core.annotation.Excel.ColumnType;
import cn.staitech.common.core.annotation.Excel.Type;
import cn.staitech.common.core.web.domain.BaseEntity;
import cn.staitech.common.core.xss.Xss;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 * 
 * @author staitech
 */
public class SysUserExt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    @Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /** 用户编码 */
    @ApiModelProperty(value = "用户编码")
    //@Excel(name = "用户编码", cellType = ColumnType.STRING, prompt = "用户编码")
    private Integer userCode;



    /** 部门ID */
    @ApiModelProperty(value = "部门名称")
    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;

    /** 机构id */
    @ApiModelProperty(value = "机构id")
    private  Long organizationId;

    /** 用户账号 */
    @ApiModelProperty(value = "登录名称")
    @Excel(name = "登录名称")
    private String userName;

    /** 用户昵称 */
    @ApiModelProperty(value = "用户名称")
    @Excel(name = "用户名称")
    private String nickName;

    /** 用户类型 */
    @ApiModelProperty(value = "用户类型")
    //@Excel(name = "用户类型")
    private String userType;

    /** 用户账号 */
    @ApiModelProperty(value = "部门名称")
    @Excel(name = "部门名称")
    private String deptN;

    /** 用户邮箱 */
    @ApiModelProperty(value = "用户邮箱")
    @Excel(name = "用户邮箱")
    private String email;

    /** 手机号码 */
    @ApiModelProperty(value = "手机号码")
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 用户性别 */
    @ApiModelProperty(value = "用户性别")
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;


    /** 密码 */
    @ApiModelProperty(value = "密码")
    private String password;

    /** 帐号状态（0正常 1停用） */
    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delFlag;

    /** 登陆状态 */
    @ApiModelProperty(value = "登陆状态")
    //@Excel(name = "登陆状态", type = Type.EXPORT)
    private String loginStatus;

    /** 最后登录IP */
    @ApiModelProperty(value = "最后登录IP")
    @Excel(name = "最后登录IP", type = Type.EXPORT)
    private String loginIp;


    /** 最后登录时间 */
    @ApiModelProperty(value = "最后登录时间")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;


    /** 角色对象 */
    @ApiModelProperty(value = "角色对象")
    private List<SysRole> roles;

    /** 角色组 不需要验证空否则Excel导入用户时报错*/
    @ApiModelProperty(value = "角色组")
    private Long[] roleIds;

    /** 岗位组 */
    @ApiModelProperty(value = "岗位组")
    private Long[] postIds;

    /** 角色ID */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;




    public SysUserExt()
    {

    }

    public SysUserExt(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    @Xss(message = "用户账号不能包含脚本字符")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }


    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDeptN() {
        return deptN;
    }

    public void setDeptN(String deptN) {
        this.deptN = deptN;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("userCode",getUserCode())
            .append("deptId", getDeptId())
            .append("organizationId",getOrganizationId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("userType",getUserType())

            .append("deptName",getDeptN())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("password", getPassword())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginStatus", getLoginStatus())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }

}
