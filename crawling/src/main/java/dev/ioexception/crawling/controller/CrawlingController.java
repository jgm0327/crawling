package dev.ioexception.crawling.controller;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.page.UploadImage;
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

    @GetMapping("/mega")
    public String crawlMega(Model model) throws IOException {
        List<Lecture> lectureList = crawlingService.getMega();
        model.addAttribute("lecture", lectureList);
        return "lecture";
    }

    @GetMapping("/ybm")
    public String crawlYbm(Model model) throws IOException, InterruptedException {
        List<Lecture> lectureList = crawlingService.getYbm();
        model.addAttribute("lecture", lectureList);
        return "lecture";
    }
    @GetMapping("/artandstudy")
    public String crawlArtandStudy(Model model) throws IOException, InterruptedException {
        List<Lecture> lectureList = crawlingService.getArtandStudy();
        
        model.addAttribute("lecture", lectureList);
        return "lecture";
    }
    @GetMapping("/goorm")
    public String crawlGoorm(Model model) throws IOException {
        List<Lecture> lectureList = crawlingService.getGoorm();

        model.addAttribute("lecture", lectureList);
        return "lecture";
    }

    @GetMapping("/inflearn")
    public void crawlInflearn() throws IOException {
        crawlingService.getInflearn();
    }

}

