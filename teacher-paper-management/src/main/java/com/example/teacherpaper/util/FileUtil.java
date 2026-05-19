package com.example.teacherpaper.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUtil {

    private final Path fileStorageLocation;

    @Value("${app.file.upload-dir:./uploads/papers}")
    private String uploadDir;

    public FileUtil() {
        if (uploadDir == null) {
            uploadDir = "./uploads/papers"; // 使用默认值
        }
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("无法创建上传目录", ex);
        }
    }

    /**
     * 存储上传的文件
     * @param file 上传的文件
     * @return 存储后的文件名
     * @throws IOException 文件操作异常
     */
    public String storeFile(MultipartFile file) throws IOException {
        // 生成唯一文件名
        String fileName = UUID.randomUUID().toString() + "_" +
                StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // 检查文件名是否合法
            if (fileName.contains("..")) {
                throw new IOException("文件名包含非法路径序列: " + fileName);
            }

            // 复制文件到目标位置
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new IOException("无法存储文件: " + fileName, ex);
        }
    }

    /**
     * 获取文件路径
     * @param fileName 文件名
     * @return 文件路径
     */
    public Path getFilePath(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize();
    }

    /**
     * 加载文件作为资源
     * @param fileName 文件名
     * @return 文件资源
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = getFilePath(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("文件不存在: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("文件不存在: " + fileName, ex);
        }
    }
}