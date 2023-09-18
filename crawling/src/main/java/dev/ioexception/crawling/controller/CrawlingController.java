package dev.ioexception.crawling.controller;

import dev.ioexception.crawling.entity.Lecture;
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

    @GetMapping("/ybm")
    public String craw(Model model) throws IOException, InterruptedException {
        List<Lecture> lectureList = crawlingService.getYbm();

        model.addAttribute("lecture", lectureList);
        return "lecture";
    }
    @GetMapping("/artandstudy")
    public String crawArtandStudy(Model model) throws IOException, InterruptedException {
        List<Lecture> lectureList = crawlingService.getArtandStudy();

        model.addAttribute("lecture", lectureList);
        return "lecture";
    }
}
