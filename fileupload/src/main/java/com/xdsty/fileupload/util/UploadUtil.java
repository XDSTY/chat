package com.xdsty.fileupload.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/13 16:41
 */
public class UploadUtil {

    private static String PREFIX_PATH = "/root/static/photo/";

    public static String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String path = PathUtil.generateRandomDir(fileName) + PathUtil.generateRandomFileName(fileName);
        String realPath = PREFIX_PATH + path;
        PathUtil.makeDirPath(realPath);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(realPath));
        out.write(file.getBytes());
        out.close();
        return path;
    }
}
