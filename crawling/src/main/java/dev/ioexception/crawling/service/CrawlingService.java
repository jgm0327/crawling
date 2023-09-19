package dev.ioexception.crawling.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.page.site.ArtandStudyCrawling;
import dev.ioexception.crawling.page.site.ClassUCrawling;
import dev.ioexception.crawling.page.site.GoormCrawling;
import dev.ioexception.crawling.page.site.MegaCrawling;
import dev.ioexception.crawling.page.site.YbmCrawling;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CrawlingService {
	private final MegaCrawling megaCrawling;
	private final GoormCrawling goormCrawling;
	private final YbmCrawling ybmCrawling;
	private final ArtandStudyCrawling artandStudyCrawling;
	private final ClassUCrawling classUCrawling;

	public List<Lecture> getMega() throws IOException {

		return megaCrawling.getSaleLecture();
	}

	public List<Lecture> getYbm() throws IOException, InterruptedException {

		return ybmCrawling.getSaleLecture();
	}

	public List<Lecture> getArtandStudy() throws IOException, InterruptedException {

		return artandStudyCrawling.getSaleLecture();
	}

	public List<Lecture> getGoorm() throws IOException {

		return goormCrawling.getSaleLecture();
	}

	public List<Lecture> getClassu() throws IOException {
		return classUCrawling.process();
	}
}
