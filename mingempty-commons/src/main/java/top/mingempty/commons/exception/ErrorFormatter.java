package top.mingempty.commons.exception;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.commons.io.UnicodeReader;
import top.mingempty.commons.trace.TraceContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异常编码格式化
 *
 * @author zzhao
 */
@Slf4j
public class ErrorFormatter {
    private static final ErrorFormatter INSTANCE = new ErrorFormatter();

    private final Map<String, Properties> errorInfosMap = new ConcurrentHashMap<>();

    public static final String DEFAULT_ERROR_PATTERN = "{0}";

    private ErrorFormatter() {
        Locale.availableLocales().forEach(locale -> {
            StringBuilder sb = new StringBuilder("/errorformatsys/errorformatsys");
            String localeToString = localeToString(locale);
            if (StrUtil.isNotEmpty(localeToString)) {
                sb.append("_")
                        .append(localeToString);
            }
            sb.append(".properties");
            URL resource = ErrorFormatter.class.getResource(sb.toString());
            if (resource != null) {
                try (InputStream inputStream = resource.openStream()) {
                    try (UnicodeReader unicodeReader = new UnicodeReader(inputStream, "UTF-8")) {
                        errorInfosMap.computeIfAbsent(localeToString, key -> new Properties())
                                .load(unicodeReader);
                    }
                } catch (IOException e) {
                    log.error("读取自定义异常配置错误", e);
                }
            }
        });


    }

    public static ErrorFormatter getInstance() {
        return INSTANCE;
    }


    public String format(String errorCode, Object... message) {
        if (errorCode != null) {
            TraceContext traceContext = TraceContext.gainTraceContext();
            if (traceContext != null) {
                Properties properties = errorInfosMap.get(localeToString(traceContext.getLocale()));
                if (properties != null) {
                    String pattern = properties.getProperty(errorCode);
                    if (pattern != null) {
                        return MessageFormat.format(pattern, message);
                    }
                }
            }
        }
        Properties properties = errorInfosMap.get(localeToString(Locale.ROOT));
        if (properties != null) {
            String pattern = properties.getProperty(errorCode);
            if (pattern != null) {
                return MessageFormat.format(pattern, message);
            }
        }
        if (message.length > 0
                && ObjUtil.isNotEmpty(message[0])) {
            return MessageFormat.format(DEFAULT_ERROR_PATTERN, message);
        }
        return null;
    }

    public void putErrorMap(String errorCode, String errorMessage) {
        if (StrUtil.isEmpty(errorCode)) {
            return;
        }
        this.errorInfosMap.computeIfAbsent(localeToString(Locale.ROOT), key -> new Properties()).put(errorCode, errorMessage);
    }


    public void putErrorMap(Locale locale, String errorCode, String errorMessage) {
        if (StrUtil.isEmpty(errorCode)) {
            return;
        }
        this.errorInfosMap.computeIfAbsent(localeToString(locale), key -> new Properties()).put(errorCode, errorMessage);
    }

    public void putErrorMap(Map<String, Object> errorMap) {
        if (MapUtil.isEmpty(errorMap)) {
            return;
        }
        this.errorInfosMap.computeIfAbsent(localeToString(Locale.ROOT), key -> new Properties()).putAll(errorMap);
    }

    public void putErrorMap(Locale locale, Map<String, Object> errorMap) {
        if (MapUtil.isEmpty(errorMap)) {
            return;
        }
        this.errorInfosMap.computeIfAbsent(localeToString(locale), key -> new Properties()).putAll(errorMap);
    }

    private String localeToString(Locale locale) {
        locale = locale == null ? Locale.ROOT : locale;
        StringBuilder sb = new StringBuilder();
        if (StrUtil.isNotEmpty(locale.getLanguage())) {
            sb.append(locale.getLanguage());

            if (StrUtil.isNotEmpty(locale.getCountry())) {
                sb.append("_")
                        .append(locale.getCountry());
            }
        }
        return sb.toString();
    }
}
