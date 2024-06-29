package top.mingempty.commons.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 解压缩工具类
 */
@Slf4j
public class CompressUtils {

    /**
     * 压缩文件
     *
     * @param sourceFilePath      要压缩的文件路径。
     * @param destinationFilePath 压缩文件的目标路径。
     * @throws IOException
     */
    public static void compressFile(String sourceFilePath, String destinationFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(destinationFilePath);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFilePath);

        zipFile(fileToZip, fileToZip.getName(), zipOut);

        zipOut.close();
        fos.close();
    }


    /**
     * 压缩目录
     *
     * @param sourceDirPath       要压缩的目录路径。
     * @param destinationFilePath 压缩文件的目标路径。
     * @throws IOException
     */
    public static void compressDirectory(String sourceDirPath, String destinationFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(destinationFilePath);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        Path sourceDir = Paths.get(sourceDirPath);

        Files.walk(sourceDir)
                .filter(path -> !Files.isDirectory(path))
                .forEach(path -> {
                    try {
                        String relativePath = sourceDir.relativize(path).toString();
                        zipFile(path.toFile(), relativePath, zipOut);
                    } catch (IOException e) {
                        log.error("压缩目录异常", e);
                    }
                });

        zipOut.close();
        fos.close();
    }

    /**
     * 解压缩文件
     *
     * @param zipFilePath           要解压缩的ZIP文件路径
     * @param destinationFolderPath 解压缩文件的目标文件夹路径。
     * @throws IOException
     */
    public static void decompressFile(String zipFilePath, String destinationFolderPath) throws IOException {
        File destDir = new File(destinationFolderPath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        FileInputStream fis = new FileInputStream(zipFilePath);
        ZipInputStream zipIn = new ZipInputStream(fis);
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            String filePath = destinationFolderPath + File.separator + entry.getName();

            if (!entry.isDirectory()) {
                extractFile(zipIn, filePath);
            } else {
                File dir = new File(filePath);
                dir.mkdir();
            }

            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }

        zipIn.close();
        fis.close();
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        fis.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[1024];
        int read;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
