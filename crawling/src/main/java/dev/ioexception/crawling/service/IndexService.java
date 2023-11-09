package dev.ioexception.crawling.service;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import dev.ioexception.crawling.aws.AWSRequestSigningApacheInterceptor;
import dev.ioexception.crawling.entity.Lecture;
import dev.ioexception.crawling.entity.LectureTag;
import dev.ioexception.crawling.entity.Tag;
import dev.ioexception.crawling.repository.LectureRepository;
import dev.ioexception.crawling.repository.LectureTagRepository;
import dev.ioexception.crawling.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.opensearch.action.DocWriteRequest;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexService {
    private final LectureRepository lectureRepository;
    private final TagRepository tagRepository;
    private final LectureTagRepository lectureTagRepository;

    private static String serviceName = "es";
    @Value("${cloud.aws.region.static}") // 서울리전
    private String region;
    private static String host = "https://search-classmoa-uofe4bd5kkz5loqmz7wk4dpiqa.ap-northeast-2.es.amazonaws.com";

    static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    public void inputIndex() throws IOException {
        log.info("------------------------------------->search1");
        RestHighLevelClient searchClient = searchClient();
        log.info("------------------------------------->Map");

        List<Lecture> lectures = lectureRepository.findAllByDate(LocalDate.now());
        for (Lecture lecture : lectures) {
            if(lecture.getImageLink().equals("no image"))continue;

            List<LectureTag> lectureTags = lectureTagRepository.getLectureTags(lecture.getLectureId());
            LectureTag lectureTag = lectureTags.get(0);
            Long tagId = lectureTag.getTagId(lectureTag.getTag());
            Optional<Tag> tag = tagRepository.findById(tagId);

            // Form the indexing request, send it, and print the response
            Map<String, Object> document = new HashMap<>();
            document.put("title", lecture.getTitle());
            document.put("instructor", lecture.getInstructor());
            document.put("companyName", lecture.getCompanyName());
            document.put("ordinaryPrice", lecture.getOrdinaryPrice());
            document.put("salePrice", lecture.getSalePrice());
            document.put("salePercent", lecture.getSalePercent());
            document.put("siteLink", lecture.getSiteLink());
            document.put("imageLink", lecture.getImageLink());
            document.put("tag", tag.get().getName());

            IndexRequest request = new IndexRequest();
            request = request.opType(DocWriteRequest.OpType.INDEX)
                    .index("search").id(lecture.getLectureId()).source(document);
            searchClient.index(request, RequestOptions.DEFAULT);

            request = request.opType(DocWriteRequest.OpType.INDEX)
                    .index("lecture").id(lecture.getLectureId()).source(document);
            searchClient.index(request, RequestOptions.DEFAULT);
        }
    }

    private RestHighLevelClient searchClient() {
        log.info("------------------------------------->RestHighLevelClient");
        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName(serviceName);
        signer.setRegionName(region);
        HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(serviceName, signer,
                credentialsProvider);
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(host))
                .setHttpClientConfigCallback(hacb -> hacb.addInterceptorLast(interceptor)));
    }
}
