package top.mingempty.mybatis.generator.base.tool;

import cn.hutool.core.io.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import top.mingempty.mybatis.generator.exception.MybatisGeneratorException;
import top.mingempty.util.WebUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 讲生成的文件压缩成zip
 */
public class GeneratorZipTool {


    @SneakyThrows
    public static void zip(String generatorPath, String subFolder) {
        Optional<HttpServletResponse> responseOptional = WebUtils.getResponse();
        HttpServletResponse response = responseOptional.orElseThrow(() -> new MybatisGeneratorException("mybatis-generator-0000000001" ));

        File sourceFile = new File(generatorPath + subFolder + File.separator);
        if (!sourceFile.exists()) {
            throw new MybatisGeneratorException("mybatis-generator-0000000002", generatorPath);
        }

        // 设置响应头
        response.setContentType("application/zip" );
        response.setCharacterEncoding("UTF-8" );
        String zipFileName = subFolder + ".zip";
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(zipFileName, StandardCharsets.UTF_8));

        try (OutputStream os = response.getOutputStream();
             ZipOutputStream zos = new ZipOutputStream(os)) {
            addFolderToZip(sourceFile, sourceFile.getName(), zos);
        } finally {
            // 删除原始文件或目录
            FileUtil.del(generatorPath);
        }
    }

    private static void addFolderToZip(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) {
                addFolderToZip(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }

            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(parentFolder + "/" + file.getName());
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            fis.close();
        }
    }
}
