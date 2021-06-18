package com.itzixue.service.center;


import com.itzixue.pojo.Users;
import com.itzixue.pojo.bo.center.CenterUserBO;

public interface CenterUserService {

    Users queryUserById(String userId);

    Users updateUser(String userId, CenterUserBO centerUserBO);

    Users updateUserFace(String userId,String faceUrl);

}
