package dev.ioexception.crawling.page;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.asynchttpclient.uri.Uri;
import org.openqa.selenium.By;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.naming.spi.DirectoryManager;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UploadImage {

//    private AmazonS3 amazonS3Client = new AmazonS3Client();
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    // 이미지 데이터를 바로 S3에 업로드
    public String uploadFromUrlToLocal(String imageUrl, String dirName, String filename) throws Exception {
        // 이미지 데이터를 바이트 배열로 읽어옴
        byte[] imageBytes = readImageBytes(imageUrl);

        // S3에 저장할 파일명 생성
        String path = "C:\\Users\\jgm03\\OneDrive\\Desktop\\project\\crawling\\crawling\\src\\main\\resources\\images\\" + dirName;

        if(imageBytes == null){
            return path + "/default/image.jpg";
        }

        File folder = new File(path);
        if(!folder.exists()){
            try {
                folder.mkdir();
            }catch (SecurityException ex){
                ex.printStackTrace();
            }
        }

        String fileName = path + "/" + filename + ".jpg";  // 확장자는 이미지 종류에 따라 변경

        ByteArrayInputStream inputStream = new ByteArrayInputStream(Objects.requireNonNull(imageBytes));

        BufferedImage bufferedImage = ImageIO.read(inputStream);
        inputStream.close();

        BufferedImage colorSpace = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = colorSpace.createGraphics();
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.drawImage(bufferedImage, 0, 0, null);
        g.dispose();

        ImageIO.write(colorSpace, "jpg", new File(fileName)); //저장하고자 하는 파일 경로를 입력합니다

        return fileName;
        // S3에 파일 업로드
//        amazonS3Client.putObject(
//                new PutObjectRequest("classmoaimage", fileName, new ByteArrayInputStream(imageBytes), objectMetadata)
//                        .withCannedAcl(CannedAccessControlList.PublicRead));
//
//        // 업로드된 이미지 URL 반환
//        return file.getPath();
    }

    private byte[] readImageBytes(String imageUrl) throws IOException{
        try {
            URL url = new URL(imageUrl);

            try (InputStream inputStream = url.openStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return outputStream.toByteArray();
            }

        } catch (Exception e) {
            return null;
        }
    }
}