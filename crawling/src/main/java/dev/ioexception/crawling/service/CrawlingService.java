package dev.ioexception.crawling.service;

import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.page.site.goormCrawlingddd;
import dev.ioexception.crawling.page.site.megaCrawlingddd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final megaCrawlingddd megaCrawling;
    private final goormCrawlingddd goormCrawling;

    public List<Lecture> getMega() throws IOException {

        return megaCrawling.getSaleLecture();
    }

    public List<Lecture> getGoorm() throws IOException {

        return goormCrawling.getSaleLecture();
    }
}
