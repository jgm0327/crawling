package dev.ioexception.crawling.controller;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.page.site.S3Test;
import dev.ioexception.crawling.service.CrawlingService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CrawlingController {
    private final CrawlingService crawlingService;
    private final S3Test s3Test;

    @GetMapping("/")
    public String craw(Model model) throws IOException, InterruptedException {
        List<Lecture> lectureList = crawlingService.getYbm();

        model.addAttribute("lecture", lectureList);
        return "lecture";
    }
    @GetMapping("/artandstu dy")
    public String crawArtandStudy(Model model) throws IOException, InterruptedException {
        List<Lecture> lectureList = crawlingService.getArtandStudy();

        model.addAttribute("lecture", lectureList);
        return "lecture";
    }

    @GetMapping("/s3test")
    public String gets3(Model model) throws IOException {
        String testUrl = s3Test.uploadFromUrlToS3("https://www.artnstudy.com/image/photo_b/cshong001.jpg", "test");

        System.out.println("testUrl = " + testUrl);
        return testUrl;
    }


}
