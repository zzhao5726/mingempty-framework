package top.mingempty.commons.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * Io工具类
 */
public class IoUtils {

    private static final String TMP_PATH = System.getProperty("java.io.tmpdir") + File.separatorChar;

    /**
     * 拷贝InputStream到另一个InputStream
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static InputStream copy(InputStream input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        baos.flush();
        return new ByteArrayInputStream(baos.toByteArray());
    }


    /**
     * 拷贝InputStream到字节数组
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] copyToByte(InputStream input) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            baos.flush();
            return baos.toByteArray();
        }
    }

    /**
     * 拷贝字节流
     */
    public static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    /**
     * 拷贝字符流（使用默认字符集）
     *
     * @param reader
     * @param writer
     * @throws IOException
     */
    public static void copy(Reader reader, Writer writer) throws IOException {
        char[] buffer = new char[4096];
        int charsRead;
        while ((charsRead = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, charsRead);
        }
    }

    /**
     * 拷贝字节流到文件
     *
     * @param input
     * @param outputFile
     * @throws IOException
     */
    public static void copy(InputStream input, File outputFile) throws IOException {
        try (OutputStream output = new FileOutputStream(outputFile)) {
            copy(input, output);
        }
    }

    /**
     * 拷贝文件到字节流
     *
     * @param inputFile
     * @param output
     * @throws IOException
     */
    public static void copy(File inputFile, OutputStream output) throws IOException {
        try (InputStream input = new FileInputStream(inputFile)) {
            copy(input, output);
        }
    }

    /**
     * 拷贝字符流到文件（使用默认字符集）
     *
     * @param reader
     * @param outputFile
     * @throws IOException
     */
    public static void copy(Reader reader, File outputFile) throws IOException {
        try (Writer writer = new FileWriter(outputFile)) {
            copy(reader, writer);
        }
    }

    /**
     * 拷贝文件到字符流（使用默认字符集）
     *
     * @param inputFile
     * @param writer
     * @throws IOException
     */
    public static void copy(File inputFile, Writer writer) throws IOException {
        try (Reader reader = new FileReader(inputFile)) {
            copy(reader, writer);
        }
    }


    /**
     * 获取文件本地地址
     *
     * @return
     */
    public static String gainParh() {
        //创建文件目录
        String filePath = TMP_PATH + UUID.randomUUID().toString() + File.separatorChar;
        if (!FileUtil.exist(filePath)) {
            FileUtil.mkdir(filePath);
        }
        return filePath;
    }


    public static String gainString(BufferedReader bufferedReader) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } catch (Exception e) {
            // 处理异常
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    // 处理异常
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String gainString(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            baos.flush();
            return baos.toString();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }
}