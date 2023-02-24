package com.shq.controller;

import com.shq.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        log.info(file.toString());

        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));

        String fileName = UUID.randomUUID() + suffix;

        File dir = new File(basePath);

        if(!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }



    @GetMapping("/download")
    public void download(String name, HttpServletResponse httpServletResponse){

        try {
            FileInputStream inputStream = new FileInputStream(new File(basePath + name));

            ServletOutputStream outputStream = httpServletResponse.getOutputStream();

            httpServletResponse.setContentType("image/jpeg");


            byte[] bytes = new byte[1024];

            while (inputStream.read(bytes) != -1){
                outputStream.write(bytes);
                outputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
