package cn.staitech.system.mapper;

import cn.staitech.system.api.domain.SpecialRole;
import cn.staitech.system.api.domain.SysUser;
import cn.staitech.system.api.domain.SysUserExt;
import cn.staitech.system.domain.user.out.OrganizationListQueryOut;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserExtMapper {
    /**
     * 根据条件分页查询用户列表
     *
     * @param SysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser SysUser);


    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);


    /**
     * 重置密码
     * @param user
     * @return
     */
    int updateUserLoginStatus(SysUser user);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);


    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public int checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUserExt checkEmailUnique(String email);

    /**
     * 校验机构号数量
     * @param organizationId
     * @return
     */
    Long checkOrganization(Long organizationId);

    /**
     *
     * @return
     */
    int updateOrganizationNum(Long organizationId);
    /**
     *
     * @return 机构列表
     */
    List<OrganizationListQueryOut> selectOrganizationList(Long organizationId);

    /**
     * 查询机构下的用户
     * @param organizationId 机构id
     * @return List<SysUser> 用户列表
     */
    List<SysUser> selectOrganization(Long organizationId);

    List<SpecialRole> querySpecialRoleListByAdmin();
    List<SpecialRole> querySpecialRoleListByUserId( Long userId);

    int selectUserCode();
}
