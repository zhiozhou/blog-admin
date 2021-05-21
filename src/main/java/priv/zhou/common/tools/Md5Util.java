package priv.zhou.common.tools;

import org.springframework.util.DigestUtils;

/**
 * MD5工具类
 *
 * @author zhou
 * @since 0.0.1
 */
public class Md5Util {

    public static String encrypt(String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes());
    }

}
