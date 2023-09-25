package dev.ioexception.crawling.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.page.site.GoormCrawling;
import dev.ioexception.crawling.service.CrawlingService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CrawlingController {
	private final CrawlingService crawlingService;

	@GetMapping("/")
	public void crawling() throws IOException, InterruptedException{
		crawlingService.getMega();
		crawlingService.getClassu();
		crawlingService.getGoorm();
		// crawlingService.getYbm();
		crawlingService.getArtandStudy();
		crawlingService.getInflearn();
	}
}

