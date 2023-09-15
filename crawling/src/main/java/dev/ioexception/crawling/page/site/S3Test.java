package dev.ioexception.crawling.page.site;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
@Component
// @RequiredArgsConstructor
public class S3Test {

	private AmazonS3 amazonS3Client = new AmazonS3Client();

	// 이미지 데이터를 바로 S3에 업로드
	public String uploadFromUrlToS3(String imageUrl, String dirName) throws IOException {
		// 이미지 데이터를 바이트 배열로 읽어옴
		byte[] imageBytes = readImageBytes(imageUrl);

		// S3에 저장할 파일명 생성
		String fileName = "test2" + ".jpg";  // 확장자는 이미지 종류에 따라 변경

		// S3에 파일 업로드
		amazonS3Client.putObject(new PutObjectRequest("classmoaimage", fileName, new ByteArrayInputStream(imageBytes), null)
			.withCannedAcl(CannedAccessControlList.PublicRead));

		// 업로드된 이미지 URL 반환
		return amazonS3Client.getUrl("classmoaimage", fileName).toString();
	}

	private byte[] readImageBytes(String imageUrl) throws IOException {
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
	}
}
