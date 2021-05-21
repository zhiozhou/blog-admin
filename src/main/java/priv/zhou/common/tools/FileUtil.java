package priv.zhou.common.tools;


import com.google.common.collect.Sets;
import org.springframework.web.multipart.MultipartFile;
import priv.zhou.common.enums.ExtEnum;

import java.io.File;
import java.util.Set;

/**
 * 文件处理工具类
 *
 * @author zhou
 * @since 0.0.1
 */
public class FileUtil {

    private FileUtil() {
    }

    /**
     * 图片类型扩展
     */
    public static final Set<String> IMAGE_EXT_SET = Sets.newHashSet(
            ExtEnum.png.name(),
            ExtEnum.jpg.name(),
            ExtEnum.jpeg.name(),
            ExtEnum.tiff.name(),
            ExtEnum.svg.name()
    );

    /**
     * excel类型扩展
     */
    public static final Set<String> EXCEL_EXT_SET = Sets.newHashSet(
            ExtEnum.xls.name(),
            ExtEnum.xlsx.name()
    );


    /**
     * 从set中匹配获取扩展名，set中不存在返回null
     */
    public static String getExtInSet(MultipartFile file, Set<String> set) {
        if (null == file) {
            return null;
        }
        String ext = getExt(file);
        if (null == ext || !set.contains(ext)) {
            return null;
        }
        return ext;
    }


    public static String getExt(File file) {
        return getExt(file.getName());
    }

    public static String getExt(MultipartFile file) {
        return getExt(file.getOriginalFilename());
    }

    /**
     * 获取扩展名
     */
    public static String getExt(String fileName) {
        int i;
        return null == fileName ? null : (i = fileName.lastIndexOf(".")) < 0 ? null : fileName.substring(i + 1).toLowerCase();
    }


    public static boolean isOverload(MultipartFile file) {
        return isOverload(file, 2);
    }

    public static boolean isOverload(MultipartFile file, int maxMb) {
        return isOverload(file, maxMb * 1024L * 1024L * 1024L);
    }

    /**
     * 文件是否过载
     */
    public static boolean isOverload(MultipartFile file, long maxByte) {
        return null != file && file.getSize() > maxByte;
    }


}
