package dev.ioexception.crawling.page.site;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.repository.LectureRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class InflearnCrawling implements CrawlingSite {
    private final LectureRepository lectureRepository;

    public InflearnCrawling(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public List<Lecture> getSaleLecture() throws IOException {
        List<Lecture> lectureList = new ArrayList<>();
        Document document = Jsoup.connect("https://www.inflearn.com/courses?discounted=true&order=seq&page=1").get();
        Elements page = document.select("footer nav div.pagination_container ul.pagination-list li:last-child");

        int lastPage = Integer.parseInt(page.select("a.pagination-link").text());

        for (int i = 1; i <= lastPage; i++) {
            document = Jsoup.connect("https://www.inflearn.com/courses?discounted=true&order=seq&page=" + i).get();
            Elements contents = document.select(
                    "main div.courses_container div.courses_card_list_body div.is-3-widescreen div.course_card_item");

            for (Element content : contents) {
                Lecture lecture = Lecture.builder()
                        .image(getImage(content))
                        .salePercent(getSalePercent(content))
                        .title(getTitle(content))
                        .url(getUrl(content))
                        .instructor(getInstructor(content))
                        .price(getPrice(content))
                        .salePrice(getSalePrice(content))
                        .build();
                lectureList.add(lecture);

                lectureRepository.save(lecture);
            }
        }

        return lectureList;
    }

    private String getImage(Element content) {

        return content.select("div.card-image figure.is_thumbnail img").attr("abs:src");
    }

    private String getSalePercent(Element content) {
        return content.select("div.card-image div.course_card_ribbon").text();
    }

    private String getTitle(Element content) {
        return content.select("div.card-content div.course_title").text();
    }

    private String getUrl(Element content) {
        return content.select("a.course_card_front").attr("abs:href");
    }

    private String getInstructor(Element content) {
        return content.select("div.card-content div.instructor").text();
    }

    private String getPrice(Element content) {
        return content.select("div.card-content div.price del").text();
    }

    private String getSalePrice(Element content) {
        return content.select("div.card-content div.price span.pay_price").text();
    }
}
