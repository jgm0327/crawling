package dev.ioexception.crawling.service;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.page.site.ArtandStudyCrawling;
import dev.ioexception.crawling.page.site.ClassUCrawling;
import dev.ioexception.crawling.page.site.GoormCrawling;
import dev.ioexception.crawling.page.site.InflearnCrawling;
import dev.ioexception.crawling.page.site.MegaCrawling;
import dev.ioexception.crawling.page.site.YbmCrawling;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CrawlingService {
	private final MegaCrawling megaCrawling;
	private final GoormCrawling goormCrawling;
	private final YbmCrawling ybmCrawling;
	private final ArtandStudyCrawling artandStudyCrawling;
	private final ClassUCrawling classUCrawling;
  private final InflearnCrawling inflearnCrawling;
  
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

	public void getClassu() throws IOException {
		classUCrawling.process();
	}
    public void getInflearn() {
        inflearnCrawling.getLecture();
    }
}
