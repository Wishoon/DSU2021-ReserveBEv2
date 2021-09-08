package com.dsu.industry.domain.product.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 변환이 실패하였습니다."));

        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImgUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);
        return uploadImgUrl;
    }

    // s3에 이미지 저장
    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    // Multifile -> File로 전환되면서 로컬에 생성된 파일을 삭제
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    // S3는 Multifile를 저장하지 못하기 때문에 File로 변환
    public Optional<File> convert(MultipartFile multiFile) throws IOException {
        File convertFile = new File(multiFile.getOriginalFilename());

        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multiFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }


    public void deleteFile(String bucket, String deleteFile) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, deleteFile));
    }
}

