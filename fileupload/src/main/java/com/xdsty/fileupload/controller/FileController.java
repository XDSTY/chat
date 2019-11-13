package com.xdsty.fileupload.controller;

import com.xdsty.fileupload.util.Result;
import com.xdsty.fileupload.util.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/13 16:37
 */
@RestController
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    /**
     * 文件上传接口
     */
    @PostMapping("upload")
    public Result uploadFile(MultipartFile file){
        String filePath;
        try{
            filePath = UploadUtil.uploadFile(file);
        }catch (Exception e){
            log.error("上传文件失败", e);
            return Result.createByFailure();
        }
        return Result.createSuccessResult(filePath);
    }

}
