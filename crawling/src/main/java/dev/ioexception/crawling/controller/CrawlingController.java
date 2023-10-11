package dev.ioexception.crawling.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.opensearch.action.DocWriteRequest;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

import dev.ioexception.crawling.aws.AWSRequestSigningApacheInterceptor;
import dev.ioexception.crawling.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
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

	@Value("${cloud.aws.region.static}")
	private String region;//ap-northeast-2 서울리전

	@GetMapping("/hi")
	public String search1() throws IOException {
		log.info("------------------------------------->search1");
		RestHighLevelClient searchClient = searchClient();

		// Create the document as a hash map
		Map<String, Object> document = new HashMap<>();
		document.put("title", "오픈서치 강의2");
		document.put("instructor", "임창준");
		document.put("companyNmae", "카클스");
		document.put("ordinaryPrice", 10000);
		document.put("salePrice", 7000);
		document.put("salePercent", "30%");
		document.put("imageLink", "naver.com");

		log.info("------------------------------------->Map");

		// Form the indexing request, send it, and print the response
		IndexRequest request1 = new IndexRequest();
		request1 = request1.opType(DocWriteRequest.OpType.INDEX).index("lecture").source(document);
		IndexResponse response = searchClient.index(request1, RequestOptions.DEFAULT);
		System.out.println(response.getResult().name());

		System.out.println("response=========>" + response.toString());
		System.out.println("response Result=========>" + response.getIndex());

		return null;

	}

	private static String serviceName = "es";
	private static String host = "https://search-classmoa-uofe4bd5kkz5loqmz7wk4dpiqa.ap-northeast-2.es.amazonaws.com";

	static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();


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

