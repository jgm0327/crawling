package dev.ioexception.crawling.service;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.page.site.ArtandStudyCrawling;
import dev.ioexception.crawling.page.site.YbmCrawling;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlingService {

    private final YbmCrawling ybmCrawling;
    private final ArtandStudyCrawling artandStudyCrawling;

    public List<Lecture> getYbm() throws IOException, InterruptedException {

        return ybmCrawling.getSaleLecture();
    }
    public List<Lecture> getArtandStudy() throws IOException, InterruptedException {

        return artandStudyCrawling.getSaleLecture();
    }
}
