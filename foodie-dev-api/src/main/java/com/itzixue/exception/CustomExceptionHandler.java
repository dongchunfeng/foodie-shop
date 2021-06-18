package com.itzixue.exception;


import com.itzixue.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JSONResult handlerMaxUploadFile(MaxUploadSizeExceededException e){
        return JSONResult.errorMsg("文件上传大小不能超过500k,请压缩图片或者降低图片质量在上传!");
    }


}
