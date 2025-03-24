package cn.staitech.common.core.utils;

import org.springframework.util.ObjectUtils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * RAS非对称加密解密
 */
public class RSAUtils {
    /**
     * 获得随机密钥对
     * @return
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得公钥
     * @param keyPair
     * @return
     */
    public static String getPublicKey(KeyPair keyPair) {
        byte[] publicBytes = keyPair.getPublic().getEncoded();
        byte[] base64Bytes = Base64.getEncoder().encode(publicBytes);
        return new String(base64Bytes);
    }

    /**
     * 获得私钥
     * @param keyPair
     * @return
     */
    public static String getPrivateKey(KeyPair keyPair) {
        byte[] privateBytes = keyPair.getPrivate().getEncoded();
        byte[] base64Bytes = Base64.getEncoder().encode(privateBytes);
        return new String(base64Bytes);
    }



    /**
     * 私钥解密
     * @param encryptedText
     * @param keyPair
     * @return
     */
    public static String decryptByPrivateKey(String encryptedText,KeyPair keyPair) {
        if (ObjectUtils.isEmpty(encryptedText)) {
            return null;
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText.getBytes());
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥解密
     * @param encryptedText
     * @param keyPair
     * @return
     */
    public static String decryptByPrivateKey2(String encryptedText,PrivateKey keyPair) {
        if (ObjectUtils.isEmpty(encryptedText)) {
            return null;
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText.getBytes());
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyPair);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过私钥获得对象
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey2(String privateKey) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey.getBytes()));
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        return keyf.generatePrivate(priPKCS8);
    }

    /**
     * 通过私钥返回数据
     * @param privateKey
     * @param str
     * @return
     * @throws Exception
     */
    public static String getStringByPrivateKey(String privateKey,String str) throws Exception {

        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey1 = keyf.generatePrivate(priPKCS8);
        return decryptByPrivateKey2(str,privateKey1);

    }

    /**
     * 公钥加密
     * @param rawText
     * @param keyPair
     * @return
     */
    public static String encryptByPublicKey(String rawText,KeyPair keyPair) {
        if (ObjectUtils.isEmpty(rawText)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] encryptedBytes = cipher.doFinal(rawText.getBytes());
            byte[] base64Bytes = Base64.getEncoder().encode(encryptedBytes);
            return new String(base64Bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
