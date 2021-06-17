package com.itzixue.service.impl;

import com.itzixue.enums.Sex;
import com.itzixue.mapper.UsersMapper;
import com.itzixue.pojo.Users;
import com.itzixue.pojo.bo.UserBO;
import com.itzixue.service.UserService;
import com.itzixue.utils.DateUtil;
import com.itzixue.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/2
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;

    public static final String USER_FACE = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606993507318&di=84ff5edf3208db0f89404579577342b6&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Fmw690%2F0071ogI5gy1gj7s0sfweoj30u00u0jt1.jpg";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        Users users = usersMapper.selectOneByExample(example);

        return users != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users addUser(UserBO userBO) {
        Users users = new Users();
        users.setId(sid.nextShort());
        users.setUsername(userBO.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.setNickname(userBO.getUsername());
        users.setBirthday(DateUtil.stringToDate("1990-01-01"));
        users.setFace(USER_FACE);
        users.setSex(Sex.secret.type);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        usersMapper.insert(users);

        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users login(String username,String password) {
        Example example = new Example(Users.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);

        return usersMapper.selectOneByExample(example);
    }
}
