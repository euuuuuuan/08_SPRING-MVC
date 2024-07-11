package com.euuuuuuan.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@Slf4j // 로그
public class FileUploadController {
    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/single-file")
    public String singleFileUpload(@RequestParam MultipartFile singleFile,
                                   @RequestParam String singleFileDescription,
                                   Model model) throws IOException {
        log.info("singleFileUpload: =====> {}", singleFile);
        log.info("singleFileDescription: =====> {}", singleFileDescription);

        // 파일 저장 공간 지정

        // 인텔리제이 root 폴더 지정
        Resource resource = resourceLoader.getResource("classpath:static/img/single");
        // classpath: 클래스의 resources까지의 경로로 가져다준다.
        String filePath = null;
        // 폴더가 있을 때, 없을 때에 따라 저장경로를 지정해주어야 한다.
        if (!resource.exists()) {
            String root = "src/main/resources/static/img/single";
            File file = new File(root);
            file.mkdir();  // 폴더 만들어주는 메소드

            filePath = file.getAbsolutePath();  // 절대경로
            log.info("폴더생성 성공, 경로: {}", filePath);
        } else {
            filePath = resourceLoader.getResource("classpath:static/img/single")
                    .getFile().getAbsolutePath();
            log.info("폴더 존재함, 경로: {}", filePath);
        }

        // 기존 파일명
        String originFileName = singleFile.getOriginalFilename();
        log.info("originFileName: {}", originFileName);  // 귀여운_사마귀.png

        // 확장자
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        log.info("ext: {}", ext);  // ext: .png

        // 저장한 파일명
        String savedName = UUID.randomUUID().toString().replace("-", "");
        log.info("savedName: {}", savedName);  // savedName: 3a82793f-3923-4f66-abb6-54231cf42bd8

        // 파일을 저장하는 것에 있어서 try catch가 필요하다.
        try {
            // 파일 저장
            // 업로드된 파일을 서버의 특정경로에 savedName으로 저장
            singleFile.transferTo(new File(filePath + "/" + savedName));

            model.addAttribute("message", "파일 업로드 성공!");
            model.addAttribute("img", "static/img/single/" + savedName);
        } catch (Exception e) {
            e.printStackTrace();

            new File(filePath + "/" + savedName).delete();
            model.addAttribute("message", "파일 업로드 실패!");
        }
        return "result";
    }
}
