package com.xdsty.chatweb.controller;

import com.xdsty.chatweb.model.vo.FileInfo;
import com.xdsty.chatweb.util.Result;
import com.xdsty.chatweb.util.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/13 16:37
 */
@RestController
@RequestMapping("file")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    /**
     * 文件上传接口
     */
    @RequestMapping("upload")
    public Result<FileInfo> uploadFile(@RequestParam(value = "file", required = true) MultipartFile file){
        try{
            return Result.createSuccessResult(UploadUtil.uploadFile(file));
        }catch (Exception e){
            return Result.createByFailure();
        }
    }

}
