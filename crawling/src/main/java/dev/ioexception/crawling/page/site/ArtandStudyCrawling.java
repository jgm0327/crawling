package dev.ioexception.crawling.page.site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.entity.LectureTag;
import dev.ioexception.crawling.entity.Tag;
import dev.ioexception.crawling.repository.LectureRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArtandStudyCrawling {

	private final LectureRepository lectureRepository;

	public List<Lecture> getSaleLecture() throws IOException {
		List<Lecture> lectureList = new ArrayList<>();


		for(int i = 1; i<=4;i++){
			Document document = Jsoup.connect("https://www.artnstudy.com/n_lecture/LecList.asp?LessonPart=" + i + "00").get();

			// 태그르 넣어준다.
			Tag tag = Tag.builder()
				.name(document.select("#topmenu1 > a").text())
				.build();

			Elements contents = document.select(
				"#content > div.wrap > div > div > ul > li:nth-child(4) > ul > a");
			for (Element content : contents) {
				Lecture lecture = Lecture.builder()
					.lecture_Id(getLectureId(content))
					.title(getTitle(content))
					.instructor(getInstructor(content))
					.company_name("ArtandStudy")
					.ordinary_price(getPrice(content))
					.sale_price(getSalePrice(content))
					.sale_percent(getSalePercent(content))
					.site_link(getUrl(content))
					.image_link(getImage(content))
					.build();
				lectureList.add(lecture);
				lectureRepository.save(lecture);
			}
		}

		return lectureList                                       ;
	}

	private String getLectureId(Element content){
		String classUrl = content.attr("href");

		// 정규 표현식 패턴
		String regex = "LessonIdx=([^&]+)";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(classUrl);

		String lessonIdx = "";
		if (matcher.find()) {
			lessonIdx = matcher.group(1);
		}
		return "artstudy" + lessonIdx;

	}

	private String getSalePrice(Element content) {
		Elements elements = content.select("li > ul > li:last-child > ul > li:nth-child(3) > span");
		if (elements.isEmpty()) {
			return getPrice(content);
		} else if (elements.text().contains("강좌추가")) {
			return getPrice(content);
		} else{
			return elements.text();
		}
	}
	private String getImage(Element content) {
		// 이미지를 가져온다.
		Element imgElement =  content.selectFirst("li > ul > li:nth-child(1) > div");
		// Style 속성값을 가져온다.
		String styleAttribute = imgElement.attr("style");
		// style 속성에서 URL 부분을 추출합니다.
		String imageUrl = styleAttribute.replaceAll(".*?url\\(([^)]+)\\).*", "$1");
		imageUrl = imageUrl.replaceAll("'", "");

		// 마지막 강의의 이미지가 없어서 정적으로 넣어줌.
		if(imageUrl.equals("/image/photo_b/sub/njPark003_2.jpg")){
			imageUrl = "/image/photo_b/sub/njPark006_2.jpg";
		}

		return "https://www.artnstudy.com" + imageUrl;
	}
	private String getUrl(Element content){
		// a태그의 href 속성을 가져온다.
		String classUrl = content.attr("href");
		return "https://www.artnstudy.com" + classUrl;
	}
	private String getPrice(Element content) {
		String price = content.select("li > ul > li:last-child > ul > li:nth-child(2) > span").text();
		if(price.equals("0원")){
			price = "무료";
		}
		return price;
	}
	private String getTitle(Element content){
		return content.select("li > ul > li:last-child > ul > li:nth-child(1) > ul > li").text();
	}
	private String getInstructor(Element content){
		String instructor = content.select("li > ul > li:last-child > ul > li:nth-child(2)").text();
		instructor = instructor.substring(0,3);
		return instructor;
	}
	private String getSalePercent(Element content) {
		String price = getPrice(content);
		String salePrice = getSalePrice(content);

		if (salePrice.equals(price)) {
			return "-1";
		}else{
			double priceValue = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
			double salePriceValue = Double.parseDouble(salePrice.replaceAll("[^\\d.]", ""));

			double discountPercentage = ((priceValue - salePriceValue) / priceValue) * 100.0;
			double roundedDiscountPercentage = Math.round(discountPercentage); // 반올림

			String discountPercentageStr = String.format("%.0f", roundedDiscountPercentage) + "%";


			return discountPercentageStr;
		}
	}
}
