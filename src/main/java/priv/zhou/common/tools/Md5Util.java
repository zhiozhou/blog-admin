package priv.zhou.common.tools;

import org.springframework.util.DigestUtils;

/**
 * @author zhou
 * @since 2019.03.11
 */
public class Md5Util {


    /**
     * MD5加密
     */
    public static String encrypt(String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes());
    }

}
