package GDG.whatssue.domain.file.service.impl;

import GDG.whatssue.domain.file.entity.ClubProfileImage;
import GDG.whatssue.domain.file.entity.MemberProfileImage;
import GDG.whatssue.domain.file.entity.PostImage;
import GDG.whatssue.domain.file.entity.UploadFile;
import GDG.whatssue.domain.file.service.FileUploadService;
import GDG.whatssue.domain.file.FileConst;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
@Service
@RequiredArgsConstructor
public class S3UploadService implements FileUploadService {

    public static String PATH = "https://whatssue.s3.ap-northeast-2.amazonaws.com/";

    //S3Config에서 Bean으로 등록한 AmazonS3Client
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    @Override
    public String uploadFile(MultipartFile multipartFile, String dirName) throws IOException {
        String storeFileName;

        //저장 사진이 없으면 기본 경로 반환
        if (multipartFile == null || multipartFile.isEmpty()) {
            return storeFileName = dirName + FileConst.DEFAULT_IMAGE_NAME;
        }

        storeFileName = getFileName(dirName, multipartFile.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(new PutObjectRequest(bucket, storeFileName, multipartFile.getInputStream(), metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));

        return storeFileName;
    }

    @Override
    public String downloadFile(String storeFileName) {
        //TODO
        return null;
    }

    @Override
    public void deleteFile(String storeFileName) {
        //default 파일이면 버킷에서 삭제하지 않는다
        if (StringUtils.substringMatch(storeFileName, 0, "default")) {
            return;
        }

        amazonS3.deleteObject(bucket, storeFileName);
    }

    @Override
    public String getOriginalFileName(MultipartFile multipartFile) {
        String originalFileName;

        if (multipartFile == null || multipartFile.isEmpty()) {
            originalFileName = "default";
        } else {
            originalFileName = multipartFile.getOriginalFilename();
        }

        return originalFileName;
    }

    private String getFileName(String dirName, String originalFileName) {
        return dirName + "/" + UUID.randomUUID() + "." + extractExt(originalFileName);
    }
    private String extractExt(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
    }
}