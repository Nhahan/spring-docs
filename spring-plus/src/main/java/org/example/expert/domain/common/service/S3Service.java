package org.example.expert.domain.common.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.ServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile file) {
        try {
            // 원본 파일명 추출
            String originalFilename = file.getOriginalFilename();
            if (!StringUtils.hasText(originalFilename)) {
                throw new ServerException("파일 이름이 존재하지 않습니다.");
            }

            // 파일 확장자 추출
            String fileExtension = getFileExtension(originalFilename);
            if (!isImageFile(fileExtension)) {
                throw new ServerException("이미지 파일만 업로드 가능합니다. (jpg, jpeg, png, gif)");
            }

            // S3에 저장할 고유한 파일명 생성 (UUID + 확장자)
            String key = "users/" + UUID.randomUUID() + fileExtension;

            // S3 파일 업로드 요청 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            // 파일 데이터를 RequestBody로 변환
            RequestBody requestBody = RequestBody.fromBytes(file.getBytes());

            // S3에 파일 업로드 실행
            s3Client.putObject(putObjectRequest, requestBody);

            // 업로드 성공 시 파일 URL 반환
            return "https://" + bucket + ".s3.amazonaws.com/" + key;
        } catch (IOException e) {
            throw new ServerException("파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 파일 확장자 추출 메서드
    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex);
    }

    // 이미지 파일 확인 메서드
    private boolean isImageFile(String fileExtension) {
        String lowerExtension = fileExtension.toLowerCase();
        return lowerExtension.equals(".jpg") || 
               lowerExtension.equals(".jpeg") || 
               lowerExtension.equals(".png") || 
               lowerExtension.equals(".gif");
    }
}
