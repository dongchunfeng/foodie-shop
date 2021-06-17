package com.itzixue.service;

import com.itzixue.pojo.Users;
import com.itzixue.pojo.bo.UserBO;

/**
 * @author MrDong
 */
public interface UserService {

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUserNameIsExist(String username);

    /**
     * 添加用户
     * @param userBO
     */
    public Users addUser(UserBO userBO);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public Users login(String username,String password);

}
