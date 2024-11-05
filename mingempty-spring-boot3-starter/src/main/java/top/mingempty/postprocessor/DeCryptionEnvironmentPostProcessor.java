package top.mingempty.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import top.mingempty.commons.util.EncDecUtil;
import top.mingempty.domain.enums.CryptoEnum;
import top.mingempty.util.ConfigEncDecUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 配置文件全局解密
 *
 * <p>
 * 解密支持三种算法:<br/>
 * 1. AES<br/>
 * 2. DES<br/>
 * 3. SM4<br/>
 * <p>
 * 使用方法示例：
 * {@snippet :
 *   //使用Aes算法进行加密
 *  System.out.println(ConfigEncDecUtil.encryptAes("123456"));
 *   //使用Des算法进行加密
 *  System.out.println(ConfigEncDecUtil.encryptDes("123456"));
 *   //使用Sm4算法进行加密
 *  System.out.println(ConfigEncDecUtil.encryptSm4("123456"));
 *
 *   //生成Aes算法自定义盐值
 *  System.out.println(ConfigEncDecUtil.generateAesSalt());
 *   //生成Des算法自定义盐值
 *  System.out.println(ConfigEncDecUtil.generateDesSalt());
 *   //生成Sm4算法自定义盐值
 *  System.out.println(ConfigEncDecUtil.generateAesSalt());
 *
 *   //生成Aes算法自定义盐值
 *  System.out.println(ConfigEncDecUtil.encryptAes("123456","11111"));
 *   //生成Des算法自定义盐值
 *  System.out.println(ConfigEncDecUtil.encryptDes("123456","11111"));
 *   //生成Sm4算法自定义盐值
 *  System.out.println(ConfigEncDecUtil.encryptSm4("123456","11111"));
 *}
 *
 *
 * <p>
 * 自定义盐值使用方法:<br/>
 * 增加下列配置项，并配置对应值。<br/>
 * me.aes-encrypt-key<br/>
 * me.des-encrypt-key<br/>
 * me.sm4-encrypt-key<br/>
 *
 * @author zzhao
 */
@Slf4j
public class DeCryptionEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private final static String ORIGIN_TRACKED_MAP_PROPERTY_SOURCE_CLASS_NAME = OriginTrackedMapPropertySource.class.getName();

    private final static String AES_REGEX = "EA\\((.*?)\\)";
    private final static Pattern AES_PATTERN = Pattern.compile(AES_REGEX);

    private final static String DES_REGEX = "ED\\((.*?)\\)";
    private final static Pattern DES_PATTERN = Pattern.compile(DES_REGEX);

    private final static String SM4_REGEX = "ES\\((.*?)\\)";
    private final static Pattern SM4_PATTERN = Pattern.compile(SM4_REGEX);


    /**
     * Post-process the given {@code environment}.
     *
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        String aesEncryptKey = environment.getProperty("me.aes-encrypt-key", ConfigEncDecUtil.AES_SALT);
        String desEncryptKey = environment.getProperty("me.des-encrypt-key", ConfigEncDecUtil.DES_SALT);
        String sm4EncryptKey = environment.getProperty("me.sm4-encrypt-key", ConfigEncDecUtil.SM4_SALT);

        environment.getPropertySources().stream()
                .filter(item -> ORIGIN_TRACKED_MAP_PROPERTY_SOURCE_CLASS_NAME.equals(item.getClass().getName()))
                .map(item -> (OriginTrackedMapPropertySource) item)
                .map(PropertySource::getSource)
                .flatMap(map -> map.entrySet().stream())
                .forEach(entry -> {
                    if ((entry.getValue() instanceof OriginTrackedValue originTrackedValue
                            && originTrackedValue.getValue() instanceof String str)) {
                        decryption(environment, aesEncryptKey, desEncryptKey, sm4EncryptKey, entry, str);
                    }

                    if (entry.getValue() instanceof String str) {
                        decryption(environment, aesEncryptKey, desEncryptKey, sm4EncryptKey, entry, str);
                    }
                });
    }

    private void decryption(ConfigurableEnvironment environment,
                            String aesEncryptKey, String desEncryptKey, String sm4EncryptKey,
                            Map.Entry<String, Object> entry, String str) {
        try {
            Matcher aesMatcher = AES_PATTERN.matcher(str);
            if (aesMatcher.find()) {
                String group = aesMatcher.group(1);
                String decrypted = EncDecUtil.decryptStrBySymmetricCrypto(CryptoEnum.AES.getValue(), aesEncryptKey, group);
                environment.getSystemProperties().put(entry.getKey(), decrypted);
            }
            Matcher desMatcher = DES_PATTERN.matcher(str);
            if (desMatcher.find()) {
                String group = desMatcher.group(1);
                String decrypted = EncDecUtil.decryptStrBySymmetricCrypto(CryptoEnum.DES.getValue(), desEncryptKey, group);
                environment.getSystemProperties().put(entry.getKey(), decrypted);
            }
            Matcher sm4Matcher = SM4_PATTERN.matcher(str);
            if (sm4Matcher.find()) {
                String group = sm4Matcher.group(1);
                String decrypted = EncDecUtil.decryptStrBySymmetricCrypto(CryptoEnum.SM4.getValue(), sm4EncryptKey, group);
                environment.getSystemProperties().put(entry.getKey(), decrypted);
            }
        } catch (Exception e) {
            log.warn("配置项[{}]解密失败", entry.getKey(), e);
        }
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 11;
    }
}
