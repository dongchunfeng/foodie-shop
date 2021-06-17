package com.itzixue.controller;


import com.itzixue.pojo.UserAddress;
import com.itzixue.pojo.bo.AddressBO;
import com.itzixue.service.AddressService;
import com.itzixue.utils.JSONResult;
import com.itzixue.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "地址相关",tags = {"地址相关的api"})
@Slf4j
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id查询用户地址",notes = "根据用户id查询用户地址",httpMethod = "POST")
    @PostMapping("/list")
    public JSONResult list(@RequestParam String userId){
        if(StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("用户id不能为空!");
        }
        List<UserAddress> userAddresses = addressService.queryAll(userId);

        return  JSONResult.ok(userAddresses);

    }

    @ApiOperation(value = "添加用户地址",notes = "添加用户地址",httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestBody AddressBO addressBO){
        JSONResult jsonResult = checkAddress(addressBO);
        if(jsonResult.getStatus()!=200){
            return jsonResult;
        }
        addressService.addNewAddress(addressBO);
        return  JSONResult.ok();
    }

    @ApiOperation(value = "修改用户地址",notes = "修改用户地址",httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@RequestBody AddressBO addressBO){

        if(StringUtils.isBlank(addressBO.getAddressId())){
            return JSONResult.errorMsg("修改地址id不能为空!");
        }

        JSONResult jsonResult = checkAddress(addressBO);
        if(jsonResult.getStatus()!=200){
            return jsonResult;
        }
        addressService.updateAddress(addressBO);
        return  JSONResult.ok();
    }

    @ApiOperation(value = "用户删除地址",notes = "用户删除地址",httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(@RequestParam String userId,@RequestParam String addressId){
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return JSONResult.errorMsg("参数不能为空!");
        }
        addressService.delAddress(userId,addressId);
        return  JSONResult.ok();
    }

    @ApiOperation(value = "用户设置默认地址",notes = "用户设置默认地址",httpMethod = "POST")
    @PostMapping("/setDefault")
    public JSONResult setDefault(@RequestParam String userId,@RequestParam String addressId){
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return JSONResult.errorMsg("参数不能为空!");
        }
        addressService.updateUserAddressToBeDefault(userId, addressId);
        return  JSONResult.ok();
    }

    private JSONResult checkAddress(AddressBO addressBO){
        String receiver = addressBO.getReceiver();
        if(StringUtils.isBlank(receiver)){
            return JSONResult.errorMsg("收货人不能为空!");
        }
        if(receiver.length()>12){
            return JSONResult.errorMsg("收货人姓名不能过长!");
        }

        String mobile = addressBO.getMobile();
        if(StringUtils.isBlank(mobile)){
            return JSONResult.errorMsg("收货人手机号不能为空!");
        }
        if(mobile.length() !=11){
            return JSONResult.errorMsg("收货人手机号长度不正确!");
        }

        boolean b = MobileEmailUtils.checkMobileIsOk(mobile);
        if(!b){
            return JSONResult.errorMsg("收货人手机号格式不正确!");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if(StringUtils.isBlank(province)
        ||StringUtils.isBlank(city)||
                StringUtils.isBlank(district)||
                StringUtils.isBlank(detail)){
            return JSONResult.errorMsg("收货人地址信息不正确!");
        }

        return JSONResult.ok();
    }

}
