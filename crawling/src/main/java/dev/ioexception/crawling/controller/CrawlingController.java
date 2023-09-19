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
	private final GoormCrawling goormCrawling;

	@GetMapping("/mega")
	public String megacraw(Model model) throws IOException {
		List<Lecture> lectureList = crawlingService.getMega();
		model.addAttribute("lecture", lectureList);
		return "lecture";
	}

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

	@GetMapping("/goorm")
	public String goormcraw(Model model) throws IOException {
		List<Lecture> lectureList = crawlingService.getGoorm();

		model.addAttribute("lecture", lectureList);
		return "lecture";
	}

	@GetMapping("/classu")
	public String crawClassu(Model model) throws IOException {
		List<Lecture> lectureList = crawlingService.getClassu();
		model.addAttribute("lecture", lectureList);
		return "lecture";
	}
}

