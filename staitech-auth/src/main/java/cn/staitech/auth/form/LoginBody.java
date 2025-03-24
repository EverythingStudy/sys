package cn.staitech.auth.form;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登录对象
 * 
 * @author staitech
 */
public class LoginBody
{
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 用户密码
     */
    private String password;

    @ApiModelProperty(value="中英文标识")
    private String language;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
