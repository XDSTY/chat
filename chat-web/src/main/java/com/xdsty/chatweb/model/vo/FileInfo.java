package com.xdsty.chatweb.model.vo;

import lombok.Data;

/**
 * description
 * @author 张富华
 * @version 1.0
 * @date 2019/11/14 10:12
 */
@Data
public class FileInfo {

    private String fileUrl;

    private String originalFilename;

    private int fileSize;

    private int width;

    private int height;
}
