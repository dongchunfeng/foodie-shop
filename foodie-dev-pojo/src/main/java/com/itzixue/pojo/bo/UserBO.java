package com.itzixue.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/3
 */
@ApiModel(value = "用户对象BO",description = "从客户端（业务层）,由用户传入的数据封装在此entity中")
public class UserBO {

    @ApiModelProperty(value = "用户名",name="username",example = "itzixue",required = true)
    private String username;
    @ApiModelProperty(value = "密码",name="password",example = "itzixue",required = true)
    private String password;
    @ApiModelProperty(value = "确认密码",name="confirmPassword",example = "itzixue",required = false)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserBO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
