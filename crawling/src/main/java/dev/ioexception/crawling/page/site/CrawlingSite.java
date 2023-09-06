package dev.ioexception.crawling.page.site;

import dev.ioexception.crawling.entity.Lecture;
import java.io.IOException;
import java.util.List;

public interface CrawlingSite {
    List<Lecture> getSaleLecture() throws IOException;
}
