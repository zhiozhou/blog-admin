package priv.zhou.common.tools;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64加解密工具类
 *
 * @author zhou
 * @since 0.1.0
 */
public class Base64Util {

    public static byte[] decode(String key) throws Exception {
        return null == key ? null : new BASE64Decoder().decodeBuffer(key);
    }

    public static String encode(byte[] key) {
        return null == key ? null : new BASE64Encoder().encodeBuffer(key);
    }
}
