package org.sbsplus.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class ImageUploadController {
    
    @Value("${upload.directory}")
    private String uploadDirectory;
    
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File uploadedFile = new File(uploadDirectory, uniqueFileName);
            FileCopyUtils.copy(file.getBytes(), uploadedFile);
            return "/uploads/" + uniqueFileName; // 클라이언트에게 이미지 URL을 반환
        }
        return "";
    }
}
