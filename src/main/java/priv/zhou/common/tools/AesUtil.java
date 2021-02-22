package priv.zhou.common.tools;

import lombok.extern.slf4j.Slf4j;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.framework.exception.GlobalException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES工具
 *
 * @author zhou
 * @since 2021.02.22
 */
@Slf4j
@SuppressWarnings("unused")
public final class AesUtil {

    /**
     * 加密算法
     */
    private static final String CIPHER_ALGORITHM = "AES";

    /**
     * 算共工作模式
     * AES是算法 CBC是工作模式 PKCS5Padding是填充模式
     */
    private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";

    /**
     * AES算法加密
     *
     * @param plantText 明文
     * @param seed      加密种子，会根据种子自动生成aes秘钥
     */
    public static String encrypt(String plantText, String seed) {
        try {
            Cipher cipher = getCipher(seed, true);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plantText.getBytes()));
        } catch (Exception e) {
            log.error("AES加密失败： plantText -->{} | seed -->{} | e -->", plantText, seed, e);
            throw new GlobalException(ResultEnum.LATER_RETRY);
        }
    }

    /**
     * AES算法解密
     *
     * @param cipherText AES加密过的密文
     * @param seed       加密种子，会根据种子自动生成aes秘钥
     */
    public static String decrypt(String cipherText, String seed) {
        try {
            Cipher cipher = getCipher(seed, false);
            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
        } catch (Exception e) {
            log.error("AES解密失败： cipherText -->{} | seed -->{} | e -->", cipherText, seed, e);
            throw new GlobalException(ResultEnum.LATER_RETRY);
        }
    }

    /**
     * 根据种子获取Cipher
     *
     * @param seed      种子
     * @param isEncrypt 是否为加密操作
     */
    private static Cipher getCipher(String seed, boolean isEncrypt) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(CIPHER_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed.getBytes());
            generator.init(128, secureRandom);
            SecretKeySpec keySpec = new SecretKeySpec(generator.generateKey().getEncoded(), CIPHER_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher;
        } catch (Exception e) {
            log.error("AES获取Cipher失败： seed -->{} | isEncrypt -->{} | e -->", seed, isEncrypt, e);
            throw new GlobalException(ResultEnum.LATER_RETRY);
        }
    }

//    public static void main(String[] args) {
//        String seed = "zhou", text = "Hello AES";
//        String encrypt = encrypt(text, seed);
//        System.out.println(encrypt);
//        String decrypt = decrypt(encrypt, seed);
//        System.out.println(decrypt);
//    }
}
