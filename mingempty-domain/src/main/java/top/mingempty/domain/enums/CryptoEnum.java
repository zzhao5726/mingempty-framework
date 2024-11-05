package top.mingempty.domain.enums;

import lombok.Getter;

/**
 * 加密解密枚举
 *
 * @author zzhao
 */
@Getter
public enum CryptoEnum {

    //非对称加密类型
    /**
     * RSA算法
     */
    RSA("RSA"),
    /**
     * RSA算法，此算法用了默认补位方式为RSA/ECB/PKCS1Padding
     */
    RSA_ECB_PKCS1("RSA/ECB/PKCS1Padding"),
    /**
     * RSA算法，此算法用了默认补位方式为RSA/ECB/NoPadding
     */
    RSA_ECB("RSA/ECB/NoPadding"),
    /**
     * RSA算法，此算法用了RSA/None/NoPadding
     */
    RSA_None("RSA/None/NoPadding"),
    SM2("SM2"),



    //摘要算法类型
    MD2("MD2"),
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512"),
    SM3("SM3"),

    //对称加密类型
    /**
     * 默认的AES加密方式：AES/ECB/PKCS5Padding
     */
    AES("AES"),
    ARCFOUR("ARCFOUR"),
    Blowfish("Blowfish"),
    /**
     * 默认的DES加密方式：DES/ECB/PKCS5Padding
     */
    DES("DES"),
    /**
     * 3DES算法，默认实现为：DESede/ECB/PKCS5Padding
     */
    DESede("DESede"),
    RC2("RC2"),

    PBEWithMD5AndDES("PBEWithMD5AndDES"),
    PBEWithSHA1AndDESede("PBEWithSHA1AndDESede"),
    PBEWithSHA1AndRC2_40("PBEWithSHA1AndRC2_40"),
    SM4("SM4"),
    ;


    /**
     * -- GETTER --
     * 获取算法字符串表示
     *
     * @return 算法字符串表示
     */
    private final String value;


    /**
     * 构造
     *
     * @param value 算法字符串表示
     */
    CryptoEnum(String value) {
        this.value = value;
    }

}
