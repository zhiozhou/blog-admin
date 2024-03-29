package priv.zhou.common.tools;

import priv.zhou.common.enums.ResultEnum;
import priv.zhou.framework.exception.RestException;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA工具类
 *
 * @author zhou
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class RsaUtil {

    private final static String CIPHER = "RSA";

    private final static String SIGN_CIPHER = "SHA256withRSA";

    /**
     * 公钥加密
     *
     * @param plainText    明文
     * @param publicKeyB64 公钥的Base64字符串
     */
    public static String encrypt(String plainText, String publicKeyB64) {
        return encrypt(plainText, getPublicKey(publicKeyB64));
    }


    /**
     * 公钥加密
     *
     * @param plainText 明文
     * @param publicKey 公钥
     */
    public static String encrypt(String plainText, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64Util.encodeToStr(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RestException(ResultEnum.LATER_RETRY);
        }
    }

    /**
     * 私钥解密
     *
     * @param cipherText    RSA密文
     * @param privateKeyB64 私钥的Base64字符串
     */
    public static String decrypt(String cipherText, String privateKeyB64) {
        return decrypt(cipherText, getPrivateKey(privateKeyB64));
    }


    /**
     * 私钥解密
     *
     * @param cipherText RSA密文
     * @param privateKey 私钥
     */
    public static String decrypt(String cipherText, PrivateKey privateKey) {
        try {
            byte[] bytes = Base64Util.decode(cipherText);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RestException(ResultEnum.LATER_RETRY);
        }
    }


    /**
     * 签名
     *
     * @param plainText  明文
     * @param privateKey 私钥
     */
    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance(SIGN_CIPHER);
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64Util.encodeToStr(privateSignature.sign());
    }


    /**
     * 验签
     *
     * @param plainText 明文
     * @param signature 签名
     * @param publicKey 公钥
     */
    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance(SIGN_CIPHER);
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
        return publicSignature.verify(Base64Util.decode(signature));
    }


    /**
     * 获取公钥
     */
    public static PublicKey getPublicKey(String keyB64) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Util.decode(keyB64));
            return KeyFactory.getInstance(CIPHER).generatePublic(keySpec);
        } catch (Exception e) {
            throw new RestException(ResultEnum.LATER_RETRY);
        }

    }


    /**
     * 获取私钥
     */
    public static PrivateKey getPrivateKey(String keyB64) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64Util.decode(keyB64));
            return KeyFactory.getInstance(CIPHER).generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RestException(ResultEnum.LATER_RETRY);
        }

    }


    /**
     * 生成公私钥对
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(CIPHER);
            generator.initialize(512, new SecureRandom());
            KeyPair keyPair = generator.generateKeyPair();

            System.out.println("公钥：" + Base64Util.encodeToStr(keyPair.getPublic().getEncoded()).replace("\r\n", ""));
            System.out.println("私钥：" + Base64Util.encodeToStr(keyPair.getPrivate().getEncoded()).replace("\r\n", ""));
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RestException(ResultEnum.LATER_RETRY);
        }
    }

    public static void main(String[] args) {
        String key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANagXPBpPJHY/6TfB+WllRvO1VJyM0353z8j48hNVai7lbwv7VIBBdb5GbX5BHBkP7Y5/2WIvGmQA9BAp1A6wtkCAwEAAQ==";
        String zhou = encrypt("zhou", "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANagXPBpPJHY/6TfB+WllRvO1VJyM0353z8j48hNVai7lbwv7VIBBdb5GbX5BHBkP7Y5/2WIvGmQA9BAp1A6wtkCAwEAAQ==");
        System.out.println(zhou);
    }


}