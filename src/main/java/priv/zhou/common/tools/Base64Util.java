package priv.zhou.common.tools;

import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;

/**
 * Base64加解密工具类
 *
 * @author zhou
 * @since 0.1.0
 */
public class Base64Util {

    public static String decodeToStr(String base64) {
        return new String(decode(base64));
    }

    public static byte[] decode(String base64) {
        return Base64.decodeBase64(base64);
    }

    public static String encodeToStr(String data) {
        return new String(encode(data));
    }

    public static byte[] encode(String data) {
        return encode(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodeToStr(byte[] data) {
        return new String(encode(data));
    }

    public static byte[] encode(byte[] data) {
        return Base64.encodeBase64(data);
    }

}
