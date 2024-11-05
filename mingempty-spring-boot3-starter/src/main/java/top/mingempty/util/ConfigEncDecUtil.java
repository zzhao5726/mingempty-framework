package top.mingempty.util;

import top.mingempty.commons.util.EncDecUtil;
import top.mingempty.domain.enums.CryptoEnum;

import static top.mingempty.commons.util.EncDecUtil.encryptStrBySymmetricCrypto;

/**
 * 配置文件加解密工具栏
 *
 * @author zzhao
 */
public class ConfigEncDecUtil {

    /**
     * 对称加密-AES内置盐值
     */
    public static final String AES_SALT = "zKAlkj0Z3C6oH8PWdgjc1Q==";

    /**
     * 对称加密-DES内置盐值
     */
    public static final String DES_SALT = "I2eS3DHp8ms=";

    /**
     * 对称加密-SM4内置盐值
     */
    public static final String SM4_SALT = "EgPqbmHvCzcTmVbR+Qrhxw==";


    /**
     * 生成对称加密算法-aes的盐值
     *
     * @return
     */
    public static String generateAesSalt() {
        return EncDecUtil.generateSaltStr(CryptoEnum.AES);
    }


    /**
     * 对称加密算法-aes加密
     *
     * @param data 要加密的数据
     * @return 加密后的数据
     */
    public static String encryptAes(String data) {
        return encryptAes(data, AES_SALT);
    }

    /**
     * 对称加密算法-aes加密，使用指定盐值
     *
     * @param data          要加密的数据
     * @param aesSaltBase64 指定盐值
     * @return 加密后的数据
     */
    public static String encryptAes(String data, String aesSaltBase64) {
        String encrypted = encryptStrBySymmetricCrypto(CryptoEnum.AES.getValue(), aesSaltBase64, data);
        return "EA(" + encrypted + ")";
    }


    /**
     * 生成对称加密算法-des的盐值
     *
     * @return
     */
    public static String generateDesSalt() {
        return EncDecUtil.generateSaltStr(CryptoEnum.DES);
    }


    /**
     * 对称加密算法-des加密
     *
     * @param data 要加密的数据
     * @return 加密后的数据
     */
    public static String encryptDes(String data) {
        return encryptDes(data, DES_SALT);
    }

    /**
     * 对称加密算法-des加密，使用指定盐值
     *
     * @param data          要加密的数据
     * @param desSaltBase64 指定盐值
     * @return 加密后的数据
     */
    public static String encryptDes(String data, String desSaltBase64) {
        String encrypted = encryptStrBySymmetricCrypto(CryptoEnum.DES.getValue(), desSaltBase64, data);
        return "ED(" + encrypted + ")";
    }


    /**
     * 生成对称加密算法-sm4的盐值
     *
     * @return
     */
    public static String generateSm4Salt() {
        return EncDecUtil.generateSaltStr(CryptoEnum.SM4);
    }


    /**
     * 对称加密算法-sm4加密
     *
     * @param data 要加密的数据
     * @return 加密后的数据
     */
    public static String encryptSm4(String data) {
        return encryptSm4(data, SM4_SALT);
    }

    /**
     * 对称加密算法-sm4加密，使用指定盐值
     *
     * @param data          要加密的数据
     * @param sm4SaltBase64 指定盐值
     * @return 加密后的数据
     */
    public static String encryptSm4(String data, String sm4SaltBase64) {
        String encrypted = encryptStrBySymmetricCrypto(CryptoEnum.SM4.getValue(), sm4SaltBase64, data);
        return "ES(" + encrypted + ")";
    }
}
