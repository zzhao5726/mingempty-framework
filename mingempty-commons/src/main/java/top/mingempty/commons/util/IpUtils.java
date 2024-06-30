package top.mingempty.commons.util;

import cn.hutool.core.net.Ipv4Util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类
 *
 * @author zzhao
 */
public class IpUtils {


    /**
     * 获取当前服务器IP
     *
     * @return 服务器IP
     */
    public static String getServerIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }


    /**
     * 获取当前服务器IP并转换为long类型
     *
     * @return 服务器IP
     */
    public static long getServerIpToLong() {
        return Ipv4Util.ipv4ToLong(getServerIp());
    }

    /**
     * 将IPv4地址转换为十六进制格式。
     *
     * @return 十六进制的IPv4地址
     */
    public static String ipv4ToHex() {
        return ipv4ToHex(getServerIp());
    }

    /**
     * 将IPv4地址转换为十六进制格式。
     *
     * @param ipv4 IPv4地址
     * @return 十六进制的IPv4地址
     */
    public static String ipv4ToHex(String ipv4) {
        String[] octets = ipv4.split("\\.");
        StringBuilder hex = new StringBuilder();

        for (String octet : octets) {
            int intVal = Integer.parseInt(octet);
            String hexVal = Integer.toHexString(intVal);

            // Ensure each hex value is two digits
            if (hexVal.length() == 1) {
                hex.append("0");
            }
            hex.append(hexVal);
        }

        return hex.toString();
    }

    /**
     * 将十六进制格式转换回IPv4地址。
     *
     * @param hex 十六进制的IPv4地址
     * @return IPv4地址
     */
    public static String hexToIpv4(String hex) {
        StringBuilder ipv4 = new StringBuilder();

        for (int i = 0; i < hex.length(); i += 2) {
            String octetHex = hex.substring(i, i + 2);
            int intVal = Integer.parseInt(octetHex, 16);
            ipv4.append(intVal);

            if (i < hex.length() - 2) {
                ipv4.append(".");
            }
        }

        return ipv4.toString();
    }


}