package cn.staitech.common.core.constant;

import io.swagger.annotations.ApiModelProperty;

/**
 * 返回状态码
 * 
 * @author staitech
 */
public class HttpStatus
{
    /**
     * 操作成功
     */
    @ApiModelProperty(value = "操作成功")
    public static final int SUCCESS = 200;

    /**
     * 对象创建成功
     */
    @ApiModelProperty(value = "对象创建成功")
    public static final int CREATED = 201;

    /**
     * 请求已经被接受
     */
    @ApiModelProperty(value = "请求已经被接受")
    public static final int ACCEPTED = 202;

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    @ApiModelProperty(value = "操作已经执行成功，但是没有返回数据")
    public static final int NO_CONTENT = 204;

    /**
     * 资源已被移除
     */
    @ApiModelProperty(value = "资源已被移除")
    public static final int MOVED_PERM = 301;

    /**
     * 重定向
     */
    @ApiModelProperty(value = "重定向")
    public static final int SEE_OTHER = 303;

    /**
     * 资源没有被修改
     */
    @ApiModelProperty(value = "资源没有被修改")
    public static final int NOT_MODIFIED = 304;

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    @ApiModelProperty(value = "参数列表错误（缺少，格式不匹配）")
    public static final int BAD_REQUEST = 400;

    /**
     * 未授权
     */
    @ApiModelProperty(value = "未授权")
    public static final int UNAUTHORIZED = 401;

    /**
     * 访问受限，授权过期
     */
    @ApiModelProperty(value = "访问受限，授权过期")
    public static final int FORBIDDEN = 403;

    /**
     * 资源，服务未找到
     */
    @ApiModelProperty(value = "资源，服务未找到")
    public static final int NOT_FOUND = 404;

    /**
     * 不允许的http方法
     */
    @ApiModelProperty(value = "不允许的http方法")
    public static final int BAD_METHOD = 405;

    /**
     * 资源冲突，或者资源被锁
     */
    @ApiModelProperty(value = "资源冲突，或者资源被锁")
    public static final int CONFLICT = 409;

    /**
     * 不支持的数据，媒体类型
     */
    @ApiModelProperty(value = "不支持的数据，媒体类型")
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * 系统内部错误
     */
    @ApiModelProperty(value = "系统内部错误")
    public static final int ERROR = 500;

    /**
     * 接口未实现
     */
    @ApiModelProperty(value = "接口未实现")
    public static final int NOT_IMPLEMENTED = 501;
}
