package com.xdsty.fileupload.util;

import java.io.File;
import java.util.UUID;

/**
 * description
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/13 16:32
 */
public class PathUtil {

    public static String generateRandomDir(String uidFileName){
        int hashCode = uidFileName.hashCode();
        int d1 = hashCode & 0xf;
        int d2 = (hashCode >> 4) & 0xf;
        return d1 + "/" + d2 + "/";
    }

    /**
     * 获取随机文件名
     */
    public static String generateRandomFileName(String fileName){
        String ext = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + ext;
    }


    public static void makeDirPath(String targetAddr) {
        File file = new File(targetAddr);
        if(!file.exists()){
            //递归的创建父目录
            file.mkdirs();
        }
    }

}
