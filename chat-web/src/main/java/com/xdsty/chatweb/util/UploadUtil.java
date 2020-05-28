package com.xdsty.chatweb.util;

import com.xdsty.chatweb.model.vo.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/13 16:41
 */
public class UploadUtil {

    private static final Logger log = LoggerFactory.getLogger(UploadUtil.class);

    private static String PREFIX_PATH = "/root/static/photo/";

    private static String IMG_PREFIX = "http://129.204.192.197/photo/";

    public static FileInfo uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String path = PathUtil.generateRandomDir(fileName);
            String randomFileName = PathUtil.generateRandomFileName(fileName);
            PathUtil.makeDirPath(PREFIX_PATH + path);
            String realPath = PREFIX_PATH + path + randomFileName;
            File targetFile = new File(realPath);
            if(!targetFile.exists()){
                if(!targetFile.createNewFile()){
                    throw new RuntimeException();
                }
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(targetFile));
            out.write(file.getBytes());
            out.flush();
            out.close();

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileUrl(IMG_PREFIX + path + randomFileName);
            fileInfo.setOriginalFilename(fileName);

            //设置图片的长宽
            fillPhotoWidthAndHeight(fileInfo, file.getInputStream());
            return fileInfo;
        }catch (Exception e){
            log.info("上传文件失败", e);
            throw new RuntimeException();
        }
    }

    /**
     * 设置图片的长宽
     * @param fileInfo 图片信息
     * @param in 图片输入流
     */
    private static void fillPhotoWidthAndHeight(FileInfo fileInfo, InputStream in) throws IOException {
        BufferedImage image = ImageIO.read(in);
        fileInfo.setWidth(image.getWidth());
        fileInfo.setHeight(image.getHeight());
    }
}
