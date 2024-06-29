package top.mingempty.cloud.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;

import java.util.Locale;

/**
 * 获取配置文件内容
 * @author zzhao
 */
@Slf4j
public class MessageSourceUtil implements MessageSourceAware {

    @Getter
    private static MessageSource MESSAGE_SOURCE;

    @Getter
    private static ResourceBundleMessageSource RESOURCE_BUNDLE_MESSAGE_SOURCE;

    @Override
    public void setMessageSource(@Nullable MessageSource messageSource) {
        MESSAGE_SOURCE = messageSource;
        if (messageSource instanceof ResourceBundleMessageSource resourceBundleMessageSource ) {
            RESOURCE_BUNDLE_MESSAGE_SOURCE = resourceBundleMessageSource;
        }
    }

    /**
     * 添加配置文件到资源国际化管理
     *
     * @param basenames
     */
    public static void addBasenames(String... basenames) {
        if (RESOURCE_BUNDLE_MESSAGE_SOURCE == null) {
            ResourceBundleMessageSource resourceBundleMessageSource = SpringContextUtil.getBean(ResourceBundleMessageSource.class);
            RESOURCE_BUNDLE_MESSAGE_SOURCE = resourceBundleMessageSource;
            MESSAGE_SOURCE = resourceBundleMessageSource;
        }

        if (RESOURCE_BUNDLE_MESSAGE_SOURCE != null) {
            RESOURCE_BUNDLE_MESSAGE_SOURCE.addBasenames(basenames);
        } else {
            log.warn("Spring Bean NotFund  ResourceBundleMessageSource");
        }
    }


    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return MESSAGE_SOURCE.getMessage(code, args, defaultMessage, Locale.CHINA);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return MESSAGE_SOURCE.getMessage(code, args, defaultMessage, locale);
    }

    public static String getMessage(String code, Object[] args) throws NoSuchMessageException {
        return getMessage(code, args, Locale.CHINA);
    }

    public static String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return MESSAGE_SOURCE.getMessage(code, args, locale);
    }

    public static String getMessage(MessageSourceResolvable resolvable) throws NoSuchMessageException {
        return MESSAGE_SOURCE.getMessage(resolvable, Locale.CHINA);
    }

    public static String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return MESSAGE_SOURCE.getMessage(resolvable, locale);
    }
}
