package com.itzixue.controller.center;

import com.itzixue.controller.BaseController;
import com.itzixue.pojo.Users;
import com.itzixue.pojo.bo.center.CenterUserBO;
import com.itzixue.resource.FileUpload;
import com.itzixue.service.center.CenterUserService;
import com.itzixue.utils.CookieUtils;
import com.itzixue.utils.DateUtil;
import com.itzixue.utils.JSONResult;
import com.itzixue.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "center - 用户信息接口", tags = "用户信息相关的api")
@RequestMapping("/userInfo")
@RestController
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping(value = "/update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户Id", required = true)
                    String userId, @RequestBody @Valid CenterUserBO centerUsersBO,
            BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = getErrors(bindingResult);
            return JSONResult.errorMap(errors);
        }
        Users userResult = centerUserService.updateUser(userId, centerUsersBO);
        userResult = setPropertyNull(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        //TODO 后续要改  增加令牌token 整合进redis 分布式会话

        return JSONResult.ok();
    }

    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("uploadFace")
    public JSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户Id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,HttpServletRequest request,HttpServletResponse response) {
        //上传的文件地址
//        String fileFace = IMAGE_USER_FACE_LOCATION;
        String fileFace = fileUpload.getImageUserFaceLocation();
        //前缀
        String uploadPathPrefix = File.separator + userId;

        if (file != null) {
            FileOutputStream fileOutputStream = null;
            try {
                //获取文件名字
                String fileName = file.getOriginalFilename();
                //分割文件名
                String[] fileNameArr = fileName.split("\\.");
                //获取后缀
                String suffix = fileNameArr[fileNameArr.length - 1];

                if(!suffix.equalsIgnoreCase("png")&&
                        !suffix.equalsIgnoreCase("jpg")&&
                !suffix.equalsIgnoreCase("jpeg")){
                    return JSONResult.errorMsg("图片格式不正确!");
                }

                String newFileName = "face-" + userId + "." + suffix;
                //上传的最终地址
                String finalFilePath = fileFace + uploadPathPrefix + File.separator + newFileName;

                //用于提供给web服务访问的地址
                uploadPathPrefix+=("/"+newFileName);

                File outFile = new File(finalFilePath);
                if(outFile.getParent()!=null){
                    outFile.getParentFile().mkdirs();
                }

                fileOutputStream  = new FileOutputStream(outFile);
                InputStream inputStream = file.getInputStream();
                IOUtils.copy(inputStream,fileOutputStream);
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if(fileOutputStream!=null){
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        } else {
            return JSONResult.errorMsg("文件不能为空!");
        }

        //获取图片访问地址
        String imageServerUrl = fileUpload.getImageServerUrl();

        //由于浏览器可能存在缓存  所以需要加上时间戳保证更新图片后会及时刷新
        String finalUserFaceUrl = imageServerUrl+uploadPathPrefix+"?t="+ DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        //更新用户头像
        Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);

        userResult = setPropertyNull(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        //TODO 后续要改  增加令牌token 整合进redis 分布式会话


        return JSONResult.ok();
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError error : fieldErrors) {
            //发生验证错误的某一个属性
            String errorField = error.getField();
            String message = error.getDefaultMessage();
            map.put(errorField, message);
        }
        return map;
    }

    private Users setPropertyNull(Users userResult) {
        userResult.setPassword(null);
        userResult.setRealname(null);
        userResult.setEmail(null);
        userResult.setMobile(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }


}
