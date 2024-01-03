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

    public void getMega() throws Exception {
        megaCrawling.getSaleLecture();
    }

    public void getYbm() throws Exception {

        ybmCrawling.getSaleLecture();
    }

    public void getArtandStudy() throws Exception{

        artandStudyCrawling.getSaleLecture();
    }

    public void getGoorm() throws Exception {

        goormCrawling.getSaleLecture();
    }

    public void getClassu() throws Exception{
        classUCrawling.process();
    }
  
    public void getInflearn() throws Exception{
        inflearnCrawling.getLecture();
    }
}
