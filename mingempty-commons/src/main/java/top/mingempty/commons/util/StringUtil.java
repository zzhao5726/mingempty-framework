package top.mingempty.commons.util;

import cn.hutool.core.util.StrUtil;

import java.text.MessageFormat;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtil {


    /**
     * 字符串判空
     *
     * @param origin
     * @return
     */
    public static String null2Str(String origin) {
        return null2Str(origin, "");
    }

    /**
     * 字符串判空
     *
     * @param origin
     * @param def
     * @return
     */
    public static String null2Str(String origin, String def) {
        return ((StrUtil.isEmpty(origin)) ? def : origin.trim());
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToUpper(String str) {
        return StrUtil.toCamelCase(str);
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String toUnderlineCase(String str) {
        return StrUtil.toUnderlineCase(str);
    }

    /**
     * 驼峰转指定连接符
     *
     * @param str
     * @return
     */
    public static String toSymbolCase(String str, char symbol) {
        return StrUtil.toSymbolCase(str, symbol);
    }

    /**
     * 将字符串的首字母转大写
     *
     * @param str 需要转换的字符串
     * @return
     */
    public static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }


    /**
     * 占位符{index}替换
     * 占位符由三种书写格式：
     * <p>
     * 　　① {index}：index为0~9之间的数字，对应对象数组中的位置。
     * <p>
     * 　　② {index，formatType}：index为0~9之间的数字，formatType为参数的格式化类型
     * <p>
     * 　　③  {index，formatType，formatStyle}：formatStyle为参数的格式化样式。
     *
     * @param str
     * @param obj
     * @return
     */
    public static String messageFormat(String str, Object... obj) {
        MessageFormat messageFormat = new MessageFormat(str);
        return messageFormat.format(obj);
    }


    /**
     * 占位符{}替换
     *
     * @param str
     * @param obj
     * @return
     */
    public static String placeholderAnalysis(String str, Object... obj) {
        return String.format(str.replaceAll("\\{[^}]*\\}", "%s"), obj);
    }

    /**
     * 将占位符 %s 替换
     *
     * @param str
     * @param obj
     * @return
     */
    public static String format(String str, Object... obj) {
        return String.format(str, obj);
    }

    /**
     * 将类名称转换为beanName
     *
     * @param className
     * @return
     */
    public static String beanName(String className) {
        String[] path = className.split("\\.");
        String beanName = path[path.length - 1];
        return Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
    }

    /**
     * 是否包含非空格字符
     *
     * @param str
     * @return 找到非空格字符，则返回true，否则返回false
     */
    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否包含汉字
     *
     * @param str
     * @return
     */
    public static boolean containsChinese(String str) {
        String regex = "[\u4e00-\u9fa5]";
        return Pattern.compile(regex).matcher(str).find();
    }

    /**
     * 校验一个字符串只允许包含汉字、大小写字母、数字、下划线，切不能以数字开头
     *
     * @param str
     * @return
     */
    public static boolean isValidString(String str) {
        return Pattern.matches("^[a-zA-Z_\\u4e00-\\u9fa5][a-zA-Z0-9_\\u4e00-\\u9fa5]*$", str);
    }

}
