package dev.ioexception.crawling.page.site;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.entity.LectureTag;
import dev.ioexception.crawling.entity.Tag;
import dev.ioexception.crawling.page.UploadImage;
import dev.ioexception.crawling.repository.LectureRepository;
import dev.ioexception.crawling.repository.LectureTagRepository;
import dev.ioexception.crawling.repository.TagRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.StringTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InflearnCrawling {
    private final LectureTagRepository lectureTagRepository;
    private final LectureRepository lectureRepository;
    private final TagRepository tagRepository;
    private final UploadImage uploadImage;

    private static final String SITE_NAME = "inflearn";

    public void getLecture() {
        Document document;
        try {
            document = Jsoup.connect("https://www.inflearn.com/courses?order=seq&types=ONLINE").get();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Elements page = document.select("footer nav div.pagination_container ul.pagination-list li:last-child");
        int lastPage = Integer.parseInt(page.select("a.pagination-link").text());


        for (int currentPage = 1; currentPage <= lastPage; currentPage++) {
            crawlPage(currentPage);
        }
    }

    public void crawlPage(int page) {
        try {
            Document documentPage = Jsoup.connect(
                    "https://www.inflearn.com/courses?order=seq&types=ONLINE&page=" + page).get();
            log.info("Crawling inflearn page: " + page);

            Elements contents = documentPage.select(
                    "main.courses_main div.courses_container div.courses_card_list_body div.is-3-widescreen div.course_card_item");

            saveLecture(contents);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLecture(Elements contents) throws IOException {
        for (Element content : contents) {
            String lectureId = getCourseNumber(content);
            String imageLink = uploadImage.uploadFromUrlToS3(getImage(content), SITE_NAME, lectureId);

            if(getPrice(content) == -2){
                continue;
            }
            Lecture lecture = Lecture.builder()
                    .lectureId(lectureId)
                    .imageLink(imageLink)
                    .salePercent(getSalePercent(content))
                    .title(getTitle(content))
                    .siteLink(getUrl(content))
                    .instructor(getInstructor(content))
                    .ordinaryPrice(getPrice(content))
                    .salePrice(getSalePrice(content))
                    .companyName(SITE_NAME)
                    .date(LocalDate.now())
                    .build();

            Lecture savedLecture = lectureRepository.findByLectureIdAndDate(lectureId, LocalDate.now())
                    .orElseGet(() -> lectureRepository.save(lecture));

            StringTokenizer tags = new StringTokenizer(getTag(content).replaceAll("·", ","), ",");

            while (tags.hasMoreTokens()) {
                String tag = tags.nextToken().trim();
                Tag tagId = existTag(tag);

                saveLecTag(savedLecture, tagId);
            }
        }
    }

    public Tag existTag(String tag) {
        return tagRepository.findByName(tag)
                .orElseGet(() -> tagRepository.save(Tag.builder().name(tag).build()));
    }

    public void saveLecTag(Lecture lecture, Tag tag) {
        LectureTag lectureTag = new LectureTag();
        lectureTag.setLecture(lecture);
        lectureTag.setTag(tag);

        lectureTagRepository.save(lectureTag);
    }

    private String getCourseNumber(Element content) {

        return SITE_NAME + content.select("div[data-productid]").attr("data-productid");
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

    private int getPrice(Element content) {
        if (content.select("div.card-content div.price span.pay_price").text().isBlank()) {
            String price = content.select("div.card-content div.price").text().replaceAll("[,\\₩]", "").trim();

            if (price.equals("무료")) {
                return 0;
            }

            if(price.equals("미설정")){
                return -2;
            }

            return Integer.parseInt(price);
        }
        return Integer.parseInt(
                content.select("div.card-content div.price del").text().replaceAll("[,\\₩]", "").trim());
    }

    private int getSalePrice(Element content) {
        String salePrice = content.select("div.card-content div.price span.pay_price").text();
        if (salePrice.isBlank()) {
            return getPrice(content);
        }
        
        return Integer.parseInt(salePrice.replaceAll("[,\\₩]", "").trim());
    }


    private String getTag(Element content) {
        return content.select("div.course_card_back div.back_course_metas div.course_categories span").text();
    }
}
