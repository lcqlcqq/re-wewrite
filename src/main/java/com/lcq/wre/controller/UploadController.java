package com.lcq.wre.controller;

import com.lcq.wre.dto.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {

    @PostMapping
    @ApiOperation("上传文件")
    public Result upload(@RequestParam("image") MultipartFile file){
//        String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
//        //上传文件到七牛云
//        //降低本身服务器的带宽消耗
//        boolean upload = qiniuUtils.upload(file, fileName);
//        if (upload){
//            //log.info(QiniuUtils.url.replace("https","http")); //qiniu上传的图片地址是http的，转回http才能在前端正确显示到其网址，也就显示预览的图片了
//            return Result.success(QiniuUtils.url.replace("https","http") + fileName);
//        }
        return Result.fail(20001,"上传失败!!!");
    }
}
