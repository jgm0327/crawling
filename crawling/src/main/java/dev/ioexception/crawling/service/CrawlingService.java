package dev.ioexception.crawling.service;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.page.site.CrawlingSite;
import dev.ioexception.crawling.page.site.InflearnCrawling;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CrawlingService {

    private final CrawlingSite crawlingSite;

    public CrawlingService(InflearnCrawling inflearnCrawling) {
        this.crawlingSite = inflearnCrawling;
    }

    public List<Lecture> getInflearn() throws IOException {

        return crawlingSite.getSaleLecture();
    }

//    public List<Lecture> getFastCampus() throws IOException {
//
//        return crawlingSite.getSaleLecture();
//    }
}
