package GDG.whatssue.global.common;

import GDG.whatssue.domain.file.entity.UploadFile;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class FileStore {

    private UploadFile storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            //예외처리 TODO
        }

        String originalFileName = multipartFile.getOriginalFilename();

        String ext = originalFileName.substring(originalFileName.lastIndexOf('.')+1);
        UUID uuid = UUID.randomUUID();

        String storeFileName = uuid + ext;
        
        //지정한 storage에 storeFileName으로 multipartFile 저장하기 TODO

        return UploadFile.builder()
            .uploadFileName(originalFileName)
            .storeFileName(storeFileName)
            .build();
    }
}