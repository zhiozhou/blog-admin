package priv.zhou.common.tools;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Copyright (C) 2012-2018 天津紫藤科技有限公司. Co. Ltd. All Rights Reserved
 *
 * @author zhou
 * @date 2021-01-06
 */
public class FileUtil {

    private FileUtil() {
    }

    public final static String XLS = "xls";

    public final static String XLSX = "xlsx";

    /**
     * 最大文件2M
     */
    public static final long MAX_FILE_SIZE = 2L * 1024 * 1024 * 1024;

    /**
     * 获取文件后缀
     */
    public static String getExt(File file) {
        return getExt(file.getName());
    }

    /**
     * 获取文件后缀
     */
    public static String getExt(MultipartFile file) {
        return getExt(file.getOriginalFilename());
    }

    /**
     * 获取文件后缀
     */
    public static String getExt(String fileName) {
        if (null == fileName) {
            return null;
        }
        int i = fileName.lastIndexOf(".");
        return i < 0 ? null : fileName.substring(i + 1);
    }


    /**
     * 文件大小是否过载
     */
    public static boolean overload(MultipartFile file) {
        return file.getSize() > MAX_FILE_SIZE;
    }

}
