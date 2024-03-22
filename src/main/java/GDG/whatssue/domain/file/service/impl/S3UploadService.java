package GDG.whatssue.domain.file.service.impl;

import GDG.whatssue.domain.file.service.FileUploadService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3UploadService implements FileUploadService {

    //S3Config에서 Bean으로 등록한 AmazonS3Client
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;


    @Override
    public String saveFile(MultipartFile multipartFile, String dirName) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();

        String ext = originalFileName.substring(originalFileName.lastIndexOf('.')+1);
        UUID uuid = UUID.randomUUID();
        String fileName = dirName + "/" + uuid + "." + ext;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    @Override
    public String downloadFile(String originalFileName) {
        //TODO
        return null;
    }

    @Override
    public void deleteFile(String originalFileName) {
        //TODO

    }
}
