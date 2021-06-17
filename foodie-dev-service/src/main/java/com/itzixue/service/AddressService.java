package com.itzixue.service;

import com.itzixue.pojo.UserAddress;
import com.itzixue.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    /**
     * 根据用户id查询用户地址列表
     * @param userId
     * @return
     */
    List<UserAddress> queryAll(String userId);

    void addNewAddress(AddressBO addressBO);

    void updateAddress(AddressBO addressBO);

    void delAddress(String userId,String addressId);

    void updateUserAddressToBeDefault(String userId,String addressId);

    /**
     * 通过地址id和用户id查询用户地址信息
     * @param userId
     * @param addressId
     * @return
     */
    UserAddress queryAddress(String userId,String addressId);

}
