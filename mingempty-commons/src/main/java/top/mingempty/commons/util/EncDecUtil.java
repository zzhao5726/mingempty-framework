package top.mingempty.commons.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import top.mingempty.domain.enums.CryptoEnum;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

/**
 * 加解密工具类
 *
 * @author zzhao
 */
public class EncDecUtil {
    //对称加密内置盐值
    private static final String AES_SALT_STR = "CMYMR9DgOdrshwWtn68MvQ==";
    private static final byte[] AES_SALT = decodeBase64(AES_SALT_STR);

    private static final String DES_SALT_STR = "eZIlAVh2wo8=";
    private static final byte[] DES_SALT = decodeBase64(DES_SALT_STR);

    private static final String SM4_SALT_STR = "SzYcI64w6XrSiwo4yZu8pg==";
    private static final byte[] SM4_SALT = decodeBase64(SM4_SALT_STR);

    //非对称加密内置盐值
    private static final String RSA_PRIVATE_SALT_STR = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKjQBEF9T/E5iJwiG8zIq2RP9mN+eUL0YQaVckm5o+wBWC0PxeaH97Ii97vLyuareWJ9qACHJQJxDmETFqjj6Pd37yB0PEYkk2x43oYX/VXnAfAobkENU0gvZoJLuEFVLVWg7ITYzvEVezHWI838yW5YVByDDhkm0w6orj8Pz5HxAgMBAAECgYA+uWQLbWNpzyXaVbYcpidA05FKMLEf1qvChX78s9SbmXhI7gZrfPPlJacFCVjv0qD8npHw+EYhHR7ppO0TffOOfQAzXThXsdkfDc2tKgJAjCMvFvkjo8PONeac6q1alvDqXhD+jBnG9K5OLFweT630K9FQX8tYVpBuRmZCMa3hDQJBAN/9XFL2u3q9B/b5nY2bS+Ao5feaSavh8qVLlfMJUJKbcbs6+9A7GghVDOOYuIIdU5hyqE31/vJhmNF4mPXC1asCQQDA8AGzHtLPwm369reza8de6tX1/szN6QjRvfWdon2JDxXVcn5PMHisDzpKy9C8w6JLghQpBzz+OFMGD111SmLTAkBzUnDMTjgsc1K8d7wEgmZIXQY5uvgfRM+3BvmHDc6sDNg7e5zWkvdOPuvJ6oFkjFDT9oRszzCZ/mG9x4eJUOzNAkEAkGgTHZoId3AzGQ/oVuZohuaF1mUrrUKqUzI20BF6nEcqNGRJncPNzhpnquv6BA75PkLSDBNa13wYyARgExXl+QJAIFuFEGR9IMDJnl1rbwkjDxrgpU7hicyOshyDtfOT4sGNwcHLIrOlCfnCt3eV8+qey12z/rT4oyKgryVZHnmUCQ==";
    private static final String RSA_PUBLIC_SALT_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCo0ARBfU/xOYicIhvMyKtkT/ZjfnlC9GEGlXJJuaPsAVgtD8Xmh/eyIve7y8rmq3lifagAhyUCcQ5hExao4+j3d+8gdDxGJJNseN6GF/1V5wHwKG5BDVNIL2aCS7hBVS1VoOyE2M7xFXsx1iPN/MluWFQcgw4ZJtMOqK4/D8+R8QIDAQAB";
    private static final byte[] RSA_PRIVATE_SALT = decodeBase64(RSA_PRIVATE_SALT_STR);
    private static final byte[] RSA_PUBLIC_SALT = decodeBase64(RSA_PUBLIC_SALT_STR);

    private static final String SM2_PRIVATE_SALT_STR = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgp9h1C21DWfi/wMuigo/hPAFRMUZo+UN34SvFa/rCSNOgCgYIKoEcz1UBgi2hRANCAATQlYRho9gaMu9WPtyVTQhgdme7g5YoBs4Ze4Ug5CxHMKJp1nW7aTFvFn1qX+r6gI3bfSoUbmM8AhInHNyADtIG";
    private static final String SM2_PUBLIC_SALT_STR = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE0JWEYaPYGjLvVj7clU0IYHZnu4OWKAbOGXuFIOQsRzCiadZ1u2kxbxZ9al/q+oCN230qFG5jPAISJxzcgA7SBg==";
    private static final byte[] SM2_PRIVATE_SALT = decodeBase64(SM2_PRIVATE_SALT_STR);
    private static final byte[] SM2_PUBLIC_SALT = decodeBase64(SM2_PUBLIC_SALT_STR);

    /*编码------start*/

    /**
     * 解码字符串密钥，可支持的编码如下：
     *
     * <pre>
     * 1. Hex（16进制）编码
     * 1. Base64编码
     * </pre>
     *
     * @param key 被解码的密钥字符串
     * @return 密钥
     */
    public static byte[] decodeSecure(String key) {
        return SecureUtil.decode(key);
    }

    /**
     * 使用Hex将字节秘钥转换为字符串秘钥
     *
     * @param salt 字节秘钥转
     * @return
     */
    public static String encodeHex(byte[] salt) {
        return HexUtil.encodeHexStr(salt);
    }

    /**
     * 使用Hex将字符串秘钥转换为字节秘钥
     *
     * @param saltStr 字符串秘钥
     * @return
     */
    public static byte[] decodeHex(String saltStr) {
        return HexUtil.decodeHex(saltStr);
    }

    /**
     * 使用Base64将字节秘钥转换为字符串秘钥
     *
     * @param salt 字节秘钥转
     * @return
     */
    public static String encodeBase64(byte[] salt) {
        return Base64.encode(salt);
    }

    /**
     * 使用Base64将字符串秘钥转换为字节秘钥
     *
     * @param saltStr 字符串秘钥
     * @return
     */
    public static byte[] decodeBase64(String saltStr) {
        return Base64.decode(saltStr);
    }
    /*编码------end*/

    /*生成盐------start*/

    /**
     * 用于非对称加密的公钥和私钥的生成
     * <p>
     * 使用Base64将字节数组转换为字符串
     *
     * @param cryptoEnum 加密解密算法枚举(只支持对非对称加密算法)
     * @return key:私钥  value:公钥
     */
    public static Pair<String, String> generatePairSaltStr(CryptoEnum cryptoEnum) {
        return generatePairSaltStr(cryptoEnum.getValue());
    }

    /**
     * 用于非对称加密的公钥和私钥的生成
     * <p>
     * 使用Base64将字节数组转换为字符串
     *
     * @param algorithm 非对称加密算法
     * @return key:私钥  value:公钥
     */
    public static Pair<String, String> generatePairSaltStr(String algorithm) {
        KeyPair keyPair = SecureUtil.generateKeyPair(algorithm);
        return Pair.of(encodeBase64(keyPair.getPrivate().getEncoded()), encodeBase64(keyPair.getPublic().getEncoded()));
    }

    /**
     * 用于非对称加密的公钥和私钥的生成
     *
     * @param cryptoEnum 加密解密算法枚举(只支持对非对称加密算法)
     * @return key:私钥  value:公钥
     */
    public static Pair<byte[], byte[]> generatePairSalt(CryptoEnum cryptoEnum) {
        return generatePairSalt(cryptoEnum.getValue());
    }

    /**
     * 用于非对称加密的公钥和私钥的生成
     *
     * @param algorithm 非对称加密算法
     * @return key:私钥  value:公钥
     */
    public static Pair<byte[], byte[]> generatePairSalt(String algorithm) {
        KeyPair keyPair = SecureUtil.generateKeyPair(algorithm);
        return Pair.of(keyPair.getPrivate().getEncoded(), keyPair.getPublic().getEncoded());
    }

    /**
     * 用于对称加密算法密钥(盐)生成
     * <p>
     * 使用Base64将字节数组转换为字符串
     *
     * @param cryptoEnum 加密解密算法枚举(只支持对称加密算法)
     * @return
     */

    public static String generateSaltStr(CryptoEnum cryptoEnum) {
        return generateSaltStr(cryptoEnum.getValue());
    }

    /**
     * 用于对称加密算法密钥(盐)生成
     * <p>
     * 使用Base64将字节数组转换为字符串
     *
     * @param algorithm 算法，支持PBE算法
     * @return
     */
    public static String generateSaltStr(String algorithm) {
        return encodeBase64(generateSalt(algorithm));
    }

    /**
     * 用于对称加密算法密钥(盐)生成
     *
     * @param cryptoEnum 加密解密算法枚举(只支持对称加密算法)
     * @return
     */
    public static byte[] generateSalt(CryptoEnum cryptoEnum) {
        return generateSalt(cryptoEnum.getValue());
    }

    /**
     * 用于对称加密算法密钥(盐)生成
     *
     * @param algorithm 算法，支持PBE算法
     * @return
     */
    public static byte[] generateSalt(String algorithm) {
        return SecureUtil.generateKey(algorithm).getEncoded();
    }
    /*生成盐------end*/


    /*对称------start*/

    /**
     * 对称加密
     *
     * @param algorithm 算法
     * @param keyBase64 秘钥（将秘钥字节数组使用Base64编码后的字符串）
     * @param data      需要加密的数据
     * @return 加密后的字节数组使用Base64编码后的字符串
     */
    public static String encryptStrBySymmetricCrypto(String algorithm, String keyBase64, String data) {
        if (data == null) {
            return null;
        }
        return encryptStrBySymmetricCrypto(algorithm, keyBase64, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 对称加密
     *
     * @param algorithm 算法
     * @param keyBase64 秘钥（将秘钥字节数组使用Base64编码后的字符串）
     * @param data      需要加密的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 加密后的字节数组使用Base64编码后的字符串
     */
    public static String encryptStrBySymmetricCrypto(String algorithm, String keyBase64, byte[] data) {
        if (keyBase64 == null) {
            return null;
        }
        return encryptStrBySymmetricCrypto(algorithm, decodeBase64(keyBase64), data);
    }

    /**
     * 对称加密
     *
     * @param algorithm 算法
     * @param key       秘钥
     * @param data      需要加密的数据
     * @return 加密后的字节数组使用Base64编码后的字符串
     */
    public static String encryptStrBySymmetricCrypto(String algorithm, byte[] key, String data) {
        if (data == null) {
            return null;
        }
        return encryptStrBySymmetricCrypto(algorithm, key, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 对称加密
     *
     * @param algorithm 算法
     * @param key       秘钥
     * @param data      需要加密的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 加密后的字节数组使用Base64编码后的字符串
     */
    public static String encryptStrBySymmetricCrypto(String algorithm, byte[] key, byte[] data) {
        byte[] encrypt = encryptBySymmetricCrypto(algorithm, key, data);
        return encodeBase64(encrypt);
    }

    /**
     * 对称加密
     *
     * @param algorithm 算法
     * @param keyBase64 秘钥（将秘钥字节数组使用Base64编码后的字符串）
     * @param data      需要加密的数据
     * @return 加密后的字节数组
     */
    public static byte[] encryptBySymmetricCrypto(String algorithm, String keyBase64, String data) {
        if (data == null) {
            return null;
        }
        return encryptBySymmetricCrypto(algorithm, keyBase64, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 对称加密
     *
     * @param algorithm 算法
     * @param keyBase64 秘钥（将秘钥字节数组使用Base64编码后的字符串）
     * @param data      需要加密的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 加密后的字节数组
     */
    public static byte[] encryptBySymmetricCrypto(String algorithm, String keyBase64, byte[] data) {
        if (keyBase64 == null) {
            return null;
        }
        return encryptBySymmetricCrypto(algorithm, decodeBase64(keyBase64), data);
    }

    /**
     * 对称加密
     *
     * @param algorithm 算法
     * @param key       秘钥
     * @param data      需要加密的数据
     * @return 加密后的字节数组
     */
    public static byte[] encryptBySymmetricCrypto(String algorithm, byte[] key, String data) {
        if (data == null) {
            return null;
        }
        return encryptBySymmetricCrypto(algorithm, key, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 对称加密
     *
     * @param algorithm 算法
     * @param key       秘钥
     * @param data      需要加密的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 加密后的字节数组
     */
    public static byte[] encryptBySymmetricCrypto(String algorithm, byte[] key, byte[] data) {
        if (algorithm == null
                || key == null
                || data == null) {
            return null;
        }
        SymmetricCrypto symmetricCrypto = symmetricCrypto(algorithm, key);
        return symmetricCrypto.encrypt(data);
    }

    /**
     * 对称解密
     *
     * @param algorithm 算法
     * @param keyBase64 秘钥（将秘钥字节数组使用Base64编码后的字符串）
     * @param data      需要解密的数据（加密后的字节数组使用Base64编码后的字符串）
     * @return 使用UTF-8字符集将解密后的字节数组转换为字符串
     */
    public static String decryptStrBySymmetricCrypto(String algorithm, String keyBase64, String data) {
        if (data == null) {
            return null;
        }
        return decryptStrBySymmetricCrypto(algorithm, keyBase64, decodeBase64(data));
    }

    /**
     * 对称解密
     *
     * @param algorithm 算法
     * @param keyBase64 秘钥（将秘钥字节数组使用Base64编码后的字符串）
     * @param data      需要解密的数据
     * @return 使用UTF-8字符集将解密后的字节数组转换为字符串
     */
    public static String decryptStrBySymmetricCrypto(String algorithm, String keyBase64, byte[] data) {
        if (keyBase64 == null) {
            return null;
        }
        return decryptStrBySymmetricCrypto(algorithm, decodeBase64(keyBase64), data);
    }

    /**
     * 对称解密
     *
     * @param algorithm 算法
     * @param key       秘钥
     * @param data      需要解密的数据（加密后的字节数组使用Base64编码后的字符串）
     * @return 使用UTF-8字符集将解密后的字节数组转换为字符串
     */
    public static String decryptStrBySymmetricCrypto(String algorithm, byte[] key, String data) {
        if (data == null) {
            return null;
        }
        return decryptStrBySymmetricCrypto(algorithm, key, decodeBase64(data));
    }

    /**
     * 对称解密
     *
     * @param algorithm 算法
     * @param key       秘钥
     * @param data      需要解密的数据
     * @return 使用UTF-8字符集将解密后的字节数组转换为字符串
     */
    public static String decryptStrBySymmetricCrypto(String algorithm, byte[] key, byte[] data) {
        byte[] decrypt = decryptBySymmetricCrypto(algorithm, key, data);
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    /**
     * 对称解密
     *
     * @param algorithm 算法
     * @param keyBase64 秘钥（将秘钥字节数组使用Base64编码后的字符串）
     * @param data      需要解密的数据（加密后的字节数组使用Base64编码后的字符串）
     * @return 解密后的字节数组
     */
    public static byte[] decryptBySymmetricCrypto(String algorithm, String keyBase64, String data) {
        if (data == null) {
            return null;
        }
        return decryptBySymmetricCrypto(algorithm, keyBase64, decodeBase64(data));
    }

    /**
     * 对称解密
     *
     * @param algorithm 算法
     * @param keyBase64 秘钥（将秘钥字节数组使用Base64编码后的字符串）
     * @param data      需要解密的数据
     * @return 解密后的字节数组
     */
    public static byte[] decryptBySymmetricCrypto(String algorithm, String keyBase64, byte[] data) {
        if (keyBase64 == null) {
            return null;
        }
        return decryptBySymmetricCrypto(algorithm, decodeBase64(keyBase64), data);
    }

    /**
     * 对称解密
     *
     * @param algorithm 算法
     * @param key       秘钥
     * @param data      需要解密的数据（加密后的字节数组使用Base64编码后的字符串）
     * @return 解密后的字节数组
     */
    public static byte[] decryptBySymmetricCrypto(String algorithm, byte[] key, String data) {
        if (data == null) {
            return null;
        }
        return decryptBySymmetricCrypto(algorithm, key, decodeBase64(data));
    }

    /**
     * 对称解密
     *
     * @param algorithm 算法
     * @param key       秘钥
     * @param data      需要解密的数据
     * @return 解密后的字节数组
     */
    public static byte[] decryptBySymmetricCrypto(String algorithm, byte[] key, byte[] data) {
        if (algorithm == null
                || key == null
                || data == null) {
            return null;
        }
        SymmetricCrypto symmetricCrypto = symmetricCrypto(algorithm, key);
        return symmetricCrypto.decrypt(data);
    }

    /**
     * 生成对称加密工具
     *
     * @param algorithm 算法
     * @param keyBase64 秘钥（将秘钥字节数组使用Base64编码后的字符串）
     * @return
     */
    public static SymmetricCrypto symmetricCrypto(String algorithm, String keyBase64) {
        return symmetricCrypto(algorithm, decodeBase64(keyBase64));
    }

    /**
     * 生成对称加密工具
     *
     * @param algorithm 算法
     * @param key       秘钥
     * @return
     */
    public static SymmetricCrypto symmetricCrypto(String algorithm, byte[] key) {
        return new SymmetricCrypto(algorithm, key);
    }
    /*对称------end*/


    /*摘要------start*/

    /**
     * 摘要
     *
     * @param algorithm 算法
     * @param data      需要提取摘要的数据
     * @return 提取到的摘要字节数组使用Base64编码后的字符串
     */
    public static String digestStr(String algorithm, String data) {
        byte[] digest = digest(algorithm, data);
        return encodeBase64(digest);
    }

    /**
     * 摘要
     *
     * @param algorithm 算法
     * @param data      需要提取摘要的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 提取到的摘要字节数组使用Base64编码后的字符串
     */
    public static String digestStr(String algorithm, byte[] data) {
        byte[] digest = digest(algorithm, data);
        return encodeBase64(digest);
    }

    /**
     * 摘要
     *
     * @param algorithm 算法
     * @param data      需要提取摘要的数据
     * @return 提取到的摘要字节数组
     */
    public static byte[] digest(String algorithm, String data) {
        if (data == null) {
            return null;
        }
        return digest(algorithm, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 摘要
     *
     * @param algorithm 算法
     * @param data      需要提取摘要的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 提取到的摘要字节数组
     */
    public static byte[] digest(String algorithm, byte[] data) {
        if (algorithm == null
                || data == null) {
            return null;
        }
        return digester(algorithm).digest(data);
    }

    /**
     * 生成对称加密工具
     *
     * @param algorithm 算法
     * @return
     */
    public static Digester digester(String algorithm) {
        return new Digester(algorithm);
    }
    /*摘要------end*/


    /*非对称------start*/

    /**
     * 非对称加密
     *
     * @param algorithm       算法
     * @param publicKeyBase64 公钥（将公钥字节数组使用Base64编码后的字符串）
     * @param data            需要加密的数据
     * @return 加密后的字节数组使用Base64编码后的字符串
     */
    public static String encryptStrByAsymmetricCrypto(String algorithm, String publicKeyBase64, String data) {
        byte[] encrypt = encryptByAsymmetricCrypto(algorithm, publicKeyBase64, data);
        return encodeBase64(encrypt);
    }

    /**
     * 非对称加密
     *
     * @param algorithm       算法
     * @param publicKeyBase64 公钥（将公钥字节数组使用Base64编码后的字符串）
     * @param data            需要加密的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 加密后的字节数组使用Base64编码后的字符串
     */
    public static String encryptStrByAsymmetricCrypto(String algorithm, String publicKeyBase64, byte[] data) {
        byte[] encrypt = encryptByAsymmetricCrypto(algorithm, publicKeyBase64, data);
        return encodeBase64(encrypt);
    }

    /**
     * 非对称加密
     *
     * @param algorithm 算法
     * @param publicKey 公钥
     * @param data      需要加密的数据
     * @return 加密后的字节数组使用Base64编码后的字符串
     */
    public static String encryptStrByAsymmetricCrypto(String algorithm, byte[] publicKey, String data) {
        byte[] encrypt = encryptByAsymmetricCrypto(algorithm, publicKey, data);
        return encodeBase64(encrypt);
    }

    /**
     * 非对称加密
     *
     * @param algorithm 算法
     * @param publicKey 公钥
     * @param data      需要加密的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 加密后的字节数组使用Base64编码后的字符串
     */
    public static String encryptStrByAsymmetricCrypto(String algorithm, byte[] publicKey, byte[] data) {
        byte[] encrypt = encryptByAsymmetricCrypto(algorithm, publicKey, data);
        return encodeBase64(encrypt);
    }

    /**
     * 非对称加密
     *
     * @param algorithm       算法
     * @param publicKeyBase64 公钥（将公钥字节数组使用Base64编码后的字符串）
     * @param data            需要加密的数据
     * @return 加密后的字节数组
     */
    public static byte[] encryptByAsymmetricCrypto(String algorithm, String publicKeyBase64, String data) {
        if (data == null) {
            return null;
        }
        return encryptByAsymmetricCrypto(algorithm, publicKeyBase64, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 非对称加密
     *
     * @param algorithm       算法
     * @param publicKeyBase64 公钥（将公钥字节数组使用Base64编码后的字符串）
     * @param data            需要加密的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 加密后的字节数组
     */
    public static byte[] encryptByAsymmetricCrypto(String algorithm, String publicKeyBase64, byte[] data) {
        if (publicKeyBase64 == null) {
            return null;
        }
        return encryptByAsymmetricCrypto(algorithm, decodeSecure(publicKeyBase64), data);
    }

    /**
     * 非对称加密
     *
     * @param algorithm 算法
     * @param publicKey 公钥
     * @param data      需要加密的数据
     * @return 加密后的字节数组
     */
    public static byte[] encryptByAsymmetricCrypto(String algorithm, byte[] publicKey, String data) {
        if (data == null) {
            return null;
        }
        return encryptByAsymmetricCrypto(algorithm, publicKey, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 非对称加密
     *
     * @param algorithm 算法
     * @param publicKey 公钥
     * @param data      需要加密的数据(使用UTF-8字符集将字符串编码为字节数组)
     * @return 加密后的字节数组
     */
    public static byte[] encryptByAsymmetricCrypto(String algorithm, byte[] publicKey, byte[] data) {
        if (algorithm == null
                || publicKey == null) {
            return null;
        }
        AsymmetricCrypto asymmetricCrypto = asymmetricCryptoByEncrypt(algorithm, publicKey);
        return asymmetricCrypto.encrypt(data, KeyType.PublicKey);
    }

    /**
     * 非对称解密
     *
     * @param algorithm        算法
     * @param privateKeyBase64 私钥（将私钥字节数组使用Base64编码后的字符串）
     * @param data             需要解密的数据（加密后的字节数组使用Base64编码后的字符串）
     * @return 使用UTF-8字符集将解密后的字节数组转换为字符串
     */
    public static String decryptStrByAsymmetricCryptoStrStr(String algorithm, String privateKeyBase64, String data) {
        byte[] decrypt = decryptStrByAsymmetricCryptoStr(algorithm, privateKeyBase64, data);
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    /**
     * 非对称解密
     *
     * @param algorithm        算法
     * @param privateKeyBase64 私钥（将私钥字节数组使用Base64编码后的字符串）
     * @param data             需要解密的数据
     * @return 使用UTF-8字符集将解密后的字节数组转换为字符串
     */
    public static String decryptStrByAsymmetricCryptoStrStr(String algorithm, String privateKeyBase64, byte[] data) {
        byte[] decrypt = decryptStrByAsymmetricCryptoStr(algorithm, privateKeyBase64, data);
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    /**
     * 非对称解密
     *
     * @param algorithm  算法
     * @param privateKey 私钥
     * @param data       需要解密的数据（加密后的字节数组使用Base64编码后的字符串）
     * @return 使用UTF-8字符集将解密后的字节数组转换为字符串
     */
    public static String decryptStrByAsymmetricCryptoStrStr(String algorithm, byte[] privateKey, String data) {
        byte[] decrypt = decryptStrByAsymmetricCryptoStr(algorithm, privateKey, data);
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    /**
     * 非对称解密
     *
     * @param algorithm  算法
     * @param privateKey 私钥
     * @param data       需要解密的数据
     * @return 使用UTF-8字符集将解密后的字节数组转换为字符串
     */
    public static String decryptStrByAsymmetricCryptoStrStr(String algorithm, byte[] privateKey, byte[] data) {
        byte[] decrypt = decryptStrByAsymmetricCryptoStr(algorithm, privateKey, data);
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    /**
     * 非对称解密
     *
     * @param algorithm        算法
     * @param privateKeyBase64 私钥（将私钥字节数组使用Base64编码后的字符串）
     * @param data             需要解密的数据（加密后的字节数组使用Base64编码后的字符串）
     * @return 解密后的字节数组
     */
    public static byte[] decryptStrByAsymmetricCryptoStr(String algorithm, String privateKeyBase64, String data) {
        if (data == null) {
            return null;
        }
        return decryptStrByAsymmetricCryptoStr(algorithm, privateKeyBase64, decodeBase64(data));
    }

    /**
     * 非对称解密
     *
     * @param algorithm        算法
     * @param privateKeyBase64 私钥（将私钥字节数组使用Base64编码后的字符串）
     * @param data             需要解密的数据
     * @return 解密后的字节数组
     */
    public static byte[] decryptStrByAsymmetricCryptoStr(String algorithm, String privateKeyBase64, byte[] data) {
        if (privateKeyBase64 == null) {
            return null;
        }
        return decryptStrByAsymmetricCryptoStr(algorithm, decodeBase64(privateKeyBase64), data);
    }

    /**
     * 非对称解密
     *
     * @param algorithm  算法
     * @param privateKey 私钥
     * @param data       需要解密的数据（加密后的字节数组使用Base64编码后的字符串）
     * @return 解密后的字节数组
     */
    public static byte[] decryptStrByAsymmetricCryptoStr(String algorithm, byte[] privateKey, String data) {
        if (data == null) {
            return null;
        }
        return decryptStrByAsymmetricCryptoStr(algorithm, privateKey, decodeBase64(data));
    }

    /**
     * 非对称解密
     *
     * @param algorithm  算法
     * @param privateKey 私钥
     * @param data       需要解密的数据
     * @return 解密后的字节数组
     */
    public static byte[] decryptStrByAsymmetricCryptoStr(String algorithm, byte[] privateKey, byte[] data) {
        if (algorithm == null
                || privateKey == null) {
            return null;
        }
        AsymmetricCrypto asymmetricCrypto = asymmetricCryptoByDecrypt(algorithm, privateKey);
        return asymmetricCrypto.decrypt(data, KeyType.PrivateKey);
    }

    /**
     * 生成非对称加密工具
     *
     * @param algorithm       算法
     * @param publicKeyBase64 公钥（将公钥字节数组使用Base64编码后的字符串）
     * @return
     */
    public static AsymmetricCrypto asymmetricCryptoByEncrypt(String algorithm, String publicKeyBase64) {
        return asymmetricCryptoByEncrypt(algorithm, decodeBase64(publicKeyBase64));
    }

    /**
     * 生成非对称加密工具
     *
     * @param algorithm 算法
     * @param publicKey 公钥
     * @return
     */
    public static AsymmetricCrypto asymmetricCryptoByEncrypt(String algorithm, byte[] publicKey) {
        return asymmetricCrypto(algorithm, null, publicKey);
    }

    /**
     * 生成非对称解密工具
     *
     * @param algorithm        算法
     * @param privateKeyBase64 私钥（将私钥字节数组使用Base64编码后的字符串）
     * @return
     */
    public static AsymmetricCrypto asymmetricCryptoByDecrypt(String algorithm, String privateKeyBase64) {
        return asymmetricCryptoByDecrypt(algorithm, decodeBase64(privateKeyBase64));
    }

    /**
     * 生成非对称解密工具
     *
     * @param algorithm  算法
     * @param privateKey 私钥
     * @return
     */
    public static AsymmetricCrypto asymmetricCryptoByDecrypt(String algorithm, byte[] privateKey) {
        return asymmetricCrypto(algorithm, privateKey, null);
    }

    /**
     * 生成非对称加解密工具
     *
     * @param algorithm        算法
     * @param privateKeyBase64 私钥（将私钥字节数组使用Base64编码后的字符串）
     * @param publicKeyBase64  公钥（将公钥字节数组使用Base64编码后的字符串）
     * @return
     */
    public static AsymmetricCrypto asymmetricCrypto(String algorithm, String privateKeyBase64, String publicKeyBase64) {
        return asymmetricCrypto(algorithm, decodeBase64(privateKeyBase64), decodeBase64(publicKeyBase64));
    }

    /**
     * 生成非对称加解密工具
     *
     * @param algorithm  算法
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return
     */
    public static AsymmetricCrypto asymmetricCrypto(String algorithm, byte[] privateKey, byte[] publicKey) {
        return new AsymmetricCrypto(algorithm, privateKey, publicKey);
    }
    /*非对称------end*/
}
