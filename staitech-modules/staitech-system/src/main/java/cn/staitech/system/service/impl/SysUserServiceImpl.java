package cn.staitech.system.service.impl;

import cn.staitech.common.core.constant.CacheConstants;
import cn.staitech.common.core.constant.UserConstants;
import cn.staitech.common.core.domain.PageResponse;
import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.exception.ServiceException;
import cn.staitech.common.core.utils.RSAUtils;
import cn.staitech.common.core.utils.SpringUtils;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.core.utils.bean.BeanUtils;
import cn.staitech.common.datascope.annotation.DataScope;
import cn.staitech.common.redis.service.RedisService;
import cn.staitech.common.security.service.TokenService;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.domain.SysRole;
import cn.staitech.system.api.domain.SysUser;
import cn.staitech.system.api.domain.SysUserExt;
import cn.staitech.system.api.model.LoginUser;
import cn.staitech.system.constant.SysProjectMenuConstant;
import cn.staitech.system.domain.SysPost;
import cn.staitech.system.domain.SysUserPost;
import cn.staitech.system.domain.SysUserRole;
import cn.staitech.system.domain.user.in.*;
import cn.staitech.system.domain.user.out.OrganizationListQueryOut;
import cn.staitech.system.domain.user.out.UserQueryByOut;
import cn.staitech.system.domain.user.out.data.UserInfoGetOut;
import cn.staitech.system.mapper.*;
import cn.staitech.system.service.ISysPermissionService;
import cn.staitech.system.service.ISysUserService;
import cn.staitech.system.utils.MessageSource;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static cn.staitech.common.core.constant.UserConstants.RSA_KEYS;
import static cn.staitech.common.core.utils.RSAUtils.getStringByPrivateKey;
import static cn.staitech.common.core.utils.SysRoleUtil.getUserCode;

/**
 * 用户 业务层处理
 *
 * @author staitech
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    private static final String USER_CODE = "user_conde";

    @Autowired
    private SysUserMapper userMapper;

    @Resource
    private SysUserExtMapper sysUserExtMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;


    @Autowired
    protected Validator validator;


    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysPermissionService permissionService;


    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 根据用户信息查询唯一数据
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public UserQueryByOut selectBy(SysUser sysUser) {
        return userMapper.selectBy(sysUser);
    }

    @Override
    public List<SysUser> selectUsers(SysUser user) {
        return sysUserExtMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public PageResponse<SysUser> selectUserListExt(UserListQeuryIn user) {
        // log.info("分页查询用户列表接口开始：");

        //创建响应
        PageResponse resp = new PageResponse();
        LoginUser loginUser;

        //获得登录人信息
        try {
            loginUser = SecurityUtils.getLoginUser();
        } catch (Exception e) {
            throw new ServiceException(MessageSource.M("GET_LOGIN_INFO_ERROR"));
        }

        //设置查询条件
        SysUser sysUser = new SysUser();
        BeanUtils.copyBeanProp(sysUser, user);
        sysUser.setLoginOrganization(loginUser.getSysUser().getOrganizationId());
        sysUser.setLoginRole(SecurityUtils.getUserId());
        Map<String, Date> createTime = user.getCreateTime();
        if (!Objects.isNull(createTime)) {
            sysUser.setBeginTime(createTime.get("beginTime"));
            sysUser.setEndTime(createTime.get("endTime"));
        }

        //分页查询
        Page<SysUser> page = PageHelper.startPage(user.getPageNum(), user.getPageSize());
        List<SysUser> sysUsers = sysUserExtMapper.selectUserList(sysUser);
        if (!CollectionUtils.isEmpty(sysUsers)) {
            sysUsers.forEach(e -> {
                e.setPassword(null);
                List<SysRole> sysRoles = roleMapper.selectRolePermissionByUserId(e.getUserId());
                List<Long> collect = sysRoles.stream().map(e1 -> e1.getRoleId()).collect(Collectors.toList());
                e.setRoleIds(collect.stream().toArray(Long[]::new));
                e.setUserCodeDesc(getUserCode(e.getUserCode()));
                e.setRoles(sysRoles);
            });
        }
        resp.setTotal(page.getTotal());
        resp.setList(sysUsers);
        resp.setPages(page.getPages());
        return resp;
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserNameExt(String userName) {
        return sysUserExtMapper.selectUserByUserName(userName);

    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {

        return sysUserExtMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserExtMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验机构信息
     *
     * @param user 用户信息
     * @return true/false
     */
    @Override
    public boolean checkOrganization(SysUser user) {
        Long i = sysUserExtMapper.checkOrganization(user.getOrganizationId());
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException(MessageSource.M("DISSALLOW_OPER_SUPER_MANAGEER"));
        }
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowedExt(SysUserExt user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException(MessageSource.M("DISSALLOW_OPER_SUPER_MANAGEER"));
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException(MessageSource.M("DATA_ACCESS_DISALLOW"));
            }
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScopeExt(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException(MessageSource.M("DATA_ACCESS_DISALLOW"));
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = sysUserExtMapper.insertUser(user);
        //修改机构授权人数
        sysUserExtMapper.updateOrganizationNum(user.getOrganizationId());
        /*// 新增用户岗位关联
        insertUserPostExt(user);
        // 新增用户与角色管理
        insertUserRoleExt(user);*/
        return rows;
    }

    /**
     * 新增保存用户信息
     *
     * @param req 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R insertUserExt(UserAddIn req) {
        // log.info("新增用户接口开始：");
        //定义变量，初始用户名密码
        String username = SecurityUtils.getUsername();
        String pwd = req.getPassword();
        String password = getString(username, pwd);

        log.info("解密后的密码" + password);
        // 业务处理
        SysUser user = new SysUser();
        BeanUtils.copyBeanProp(user, req);

        if (UserConstants.NOT_UNIQUE.equals(checkUserNameUnique(user.getUserName()))) {
            return R.fail(MessageSource.M("ADD_USER").concat("'").concat(user.getUserName()).concat("'").concat(MessageSource.M("ADD_USER_ERROR_ACCOUNT_HAD")));
        }
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && UserConstants.NOT_UNIQUE.equals(
                checkPhoneUnique(user))) {
            return R.fail(MessageSource.M("ADD_USER").concat("'").concat(user.getUserName()).concat("'").concat(MessageSource.M("ADD_USER_ERROR_PHONE_HAD")));
        }
        if (!checkOrganization(user)) {
            return R.fail(MessageSource.M("ADD_USER").concat("'").concat(user.getUserName()).concat("'").concat(MessageSource.M("ADD_USER_ERROR_ORG_COUNT")));
        }
        if (!checkPwd(password)) {
            return R.fail(MessageSource.M("ADD_USER").concat("'").concat(user.getUserName()).concat("'").concat(MessageSource.M("ADD_USER_ERROR_PASSWORD_FORMART")));
        }

        user.setCreateBy(SecurityUtils.getUserId());
        user.setPassword(SecurityUtils.encryptPassword(password));
        //todo 机构内排序还是全局排序
        Integer userCode = sysUserExtMapper.selectUserCode();

        user.setUserCode(userCode + 1);
        // 新增用户信息
        int rows = sysUserExtMapper.insertUser(user);
        //修改机构授权人数
        sysUserExtMapper.updateOrganizationNum(user.getOrganizationId());

        // 新增用户与角色管理
        insertUserRole(user);


        return R.ok(rows);
    }

    /**
     * 解析密码
     *
     * @param username
     * @param pwd
     * @return
     */
    private String getString(String username, String pwd) {
        String password;
        //获得私钥解析密码
        String cacheObject = redisService.getCacheObject(RSA_KEYS + username);
        if (Objects.isNull(cacheObject)) {
            throw new ServiceException(MessageSource.M("GET_REDIS_KEY_ERROR"));
        }
        try {
            password = RSAUtils.getStringByPrivateKey(cacheObject, pwd);
        } catch (Exception e) {
            throw new ServiceException(MessageSource.M("PASSWORD_RSA_DECODE_ERROR") + e);
        }

        //每次解析完删除密钥对
        redisService.deleteObject(RSA_KEYS + username);
        return password;
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改保存用户信息
     *
     * @param req 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateUserExt(UserUpdateIn req) {
        SysUser user = new SysUser();
        BeanUtils.copyBeanProp(user, req);
        checkUserAllowed(user);
        //userService.checkUserDataScope(user.getUserId());
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && UserConstants.NOT_UNIQUE.equals(
                checkPhoneUnique(user))) {
            return R.fail(MessageSource.M("UPDATE_USER").concat("'").concat(user.getNickName()).concat("'").concat(MessageSource.M("ADD_USER_ERROR_PHONE_HAD")));
        }
        // 查询更新前的数据
        SysUser userBy = sysUserExtMapper.selectUserById(req.getUserId());
        user.setUpdateBy(SecurityUtils.getUserId());
        if (!req.getOrganizationId().equals(userBy.getOrganizationId())) {
            if (!checkOrganization(user)) {
                return R.fail(MessageSource.M("UPDATE_USER").concat("'").concat(user.getNickName()).concat("'").concat(MessageSource.M("ADD_USER_ERROR_ORG_COUNT")));
            }
        }

        int i = sysUserExtMapper.updateUser(user);

        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(req.getUserId());

        // 新增用户与角色管理
        insertUserRole(user);

        if (req.getOrganizationId() != null) {
            // 更新修改之前的机构信息
            sysUserExtMapper.updateOrganizationNum(userBy.getOrganizationId());
        }
        //修改机构授权人数
        sysUserExtMapper.updateOrganizationNum(user.getOrganizationId());
        return R.ok(i);
    }

    /**
     * 密码修改
     *
     * @param req
     * @return
     */

    public R updatePwd(PassWordUpdateIn req) {
        //获得用户名
        String username = SecurityUtils.getUsername();
        SysUser user = sysUserExtMapper.selectUserByUserName(username);
        String password = user.getPassword();
        if (!SecurityUtils.matchesPassword(req.getOldPassword(), password)) {
            return R.fail(MessageSource.M("ADD_UPDATE_PASSWORD_ERROR_OLD"));
        }
        //获取密钥对解密密码
        String cacheObject = redisService.getCacheObject(RSA_KEYS + username);
        if (Objects.isNull(cacheObject)) {
            return R.fail(MessageSource.M("GET_REDIS_KEY_ERROR"));
        }
        String newPassword;
        //解析
        try {
            newPassword = getStringByPrivateKey(cacheObject, req.getNewPassword());
        } catch (Exception e) {
            throw new ServiceException(MessageSource.M("PASSWORD_RSA_DECODE_ERROR") + e);
        }
        //每次解析完删除密钥对
        redisService.deleteObject(RSA_KEYS + username);

        if (!checkPwd(newPassword)) {
            return R.fail(MessageSource.M("NEW_PASSWORD_FORMART_ERROR"));
        }

        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return R.fail(MessageSource.M("ADD_UPDATE_PASSWORD_ERROR_SAME"));
        }

        if (resetUserPwdExt(username, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            LoginUser loginUser = SecurityUtils.getLoginUser();
            loginUser.getSysUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);

        }
        return R.ok();
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户状态
     *
     * @param req 用户信息
     * @return 结果
     */
    @Override
    public R updateUserStatusExt(ChangeStatusIn req) {
        SysUser user = new SysUser();
        user.setUserId(req.getUserId());
        checkUserAllowed(user);
        user.setStatus(req.getStatus());
        user.setUpdateBy(SecurityUtils.getUserId());
        SysUser sysUser = userMapper.selectById(req.getUserId());

        int i = userMapper.updateUser(user);
        //禁用时登陆人退出
        if ("1".equals(req.getStatus())) {
            String userName = sysUser.getUserName();
            String cacheObject = redisService.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY + userName);
            if (StringUtils.isNotEmpty(cacheObject)) {
                redisService.deleteObject(cacheObject);
                redisService.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + userName);
            }

        }

        return R.ok();
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param userId 用户信息
     * @return 结果
     */
    @Override
    public R resetPwd(Long userId) {
        SysUser user = new SysUser();

        user.setUserId(userId);
        checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(SysProjectMenuConstant.INITIAL_PASSWORD));
        user.setLoginStatus(SysProjectMenuConstant.FIRST_LOGIN);
        user.setUpdateBy(SecurityUtils.getUserId());
        SysUser sysUser = userMapper.selectById(userId);
        sysUserExtMapper.updateUserLoginStatus(user);
        //重置后删除redis信息
        String userName = sysUser.getUserName();
        String cacheObject = redisService.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY + userName);
        if (StringUtils.isNotEmpty(cacheObject)) {
            redisService.deleteObject(cacheObject);
            redisService.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + userName);
        }
        return R.ok(SysProjectMenuConstant.INITIAL_PASSWORD);

    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwdExt(String userName, String password) {
        return sysUserExtMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRoleExt(SysUserExt user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPostExt(SysUserExt user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotNull(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(Long[] userIds) {
        List<Long> organizationList = new ArrayList<>();
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
            // 删除前查询信息
            SysUser userBy = sysUserExtMapper.selectUserById(userId);
            organizationList.add(userBy.getOrganizationId());
            /*checkUserDataScope(userId);*/
        }
        // 删除用户与角色关联
        // userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        // userPostMapper.deleteUserPost(userIds);
        int res = userMapper.deleteUserByIds(userIds);
        for (Long i : organizationList) {
            sysUserExtMapper.updateOrganizationNum(i);
        }
        return res;
    }


    /**
     * 机构列表
     *
     * @return
     */
    @Override
    public List<OrganizationListQueryOut> selectOrganizationList() {
        Long userId = SecurityUtils.getUserId();
        Long organizationId = SecurityUtils.getLoginUser().getSysUser().getOrganizationId();
        if (SysUser.isAdmin(userId)) {
            return sysUserExtMapper.selectOrganizationList(null);
        } else {
            return sysUserExtMapper.selectOrganizationList(organizationId);
        }
    }

    /**
     * 用户信息
     *
     * @return
     */
    @Override
    public UserInfoGetOut getInfo() {
        Long userId = SecurityUtils.getUserId();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(userId);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(userId);
        UserInfoGetOut resp = new UserInfoGetOut();
        resp.setUser(selectUserById(userId));
        resp.setRoles(roles);
        resp.setPermissions(permissions);
        return resp;
    }


    /**
     * 查用户角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Long> selectRoleByUser(Long userId) {
        return roleMapper.selectRoleByUser(userId);

    }

    private boolean checkPwd(String pwd) {
        String rex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*[@#$%^&+=]).{6,20}$";
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }
}
