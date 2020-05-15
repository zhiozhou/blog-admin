package priv.zhou.common.tools;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AesUtil {

    private final static String ALGORITHM = "AES";

    public static String newKey() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
        generator.init(128);//要生成多少位，只需要修改这里即可128, 192或256
        SecretKey secretKey = generator.generateKey();
        byte[] b = secretKey.getEncoded();
        return byteToHexStr(b);
    }

    /**
     * 将二进制转换成16进制
     */
    public static String byteToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * 将16进制转换为二进制
     */
    public static byte[] hexStrToByte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
