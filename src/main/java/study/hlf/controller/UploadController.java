package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import study.hlf.dto.ImageUploadDto;
import study.hlf.service.AwsS3Service;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UploadController {

    private final AwsS3Service awsS3Service;

    @PostMapping("/images/upload")
    public ImageUploadDto upload(@RequestParam MultipartFile file){
        String url = awsS3Service.uploadFile(file);
        return new ImageUploadDto(url);
    }

}
