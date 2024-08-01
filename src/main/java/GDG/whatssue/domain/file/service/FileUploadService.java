package GDG.whatssue.domain.file.service;

import GDG.whatssue.domain.file.entity.UploadFile;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileUploadService {

    public String uploadFile(MultipartFile multipartFile, String dirName) throws IOException;
    public String downloadFile(String uploadFileName);
    public void deleteFile(String uploadFileName);

    String getOriginalFileName(MultipartFile multipartFile);
}
