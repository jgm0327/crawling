package dev.ioexception.crawling.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ioexception.crawling.service.CrawlingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CrawlingController {
	private final CrawlingService crawlingService;

	@GetMapping("/")
	public void crawling() throws IOException, InterruptedException {
		crawlingService.getMega();
		crawlingService.getClassu();
		crawlingService.getGoorm();
		// crawlingService.getYbm();
		crawlingService.getArtandStudy();
		crawlingService.getInflearn();
	}
}

