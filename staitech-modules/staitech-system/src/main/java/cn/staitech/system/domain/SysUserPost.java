package cn.staitech.system.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户和岗位关联 sys_user_post
 * 
 * @author staitech
 */
@Api(value="用户和岗位关联 ",tags="用户和岗位关联 ")
public class SysUserPost
{
    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    
    /** 岗位ID */
    @ApiModelProperty(value = "岗位ID")
    private Long postId;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("postId", getPostId())
            .toString();
    }
}
