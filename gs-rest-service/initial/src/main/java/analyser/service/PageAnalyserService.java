package analyser.service;

import analyser.dto.PageData;
import analyser.entity.PageDataEntity;
import analyser.repository.PageRepository;
import analyser.sentiment.SentimentHTTPClient;
import analyser.util.PageUtil;
import com.aylien.textapi.responses.Sentiment;
import com.sun.javaws.exceptions.InvalidArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

import static analyser.util.EntityMapper.toDto;
import static analyser.util.EntityMapper.toEntity;

@Service
@Slf4j
public class PageAnalyserService {
    @Autowired
    SentimentHTTPClient sentimentHTTPClient;

    @Autowired
    PageRepository pageRepository;

    public PageData parsePage(String urlString) throws InvalidArgumentException {

        //check if record already exists in database
        try {
            if (findByUrl(urlString) != null) {
                return findByUrl(urlString);
            }
        } catch (NoSuchElementException e) {
            // Do Nothing. Proceed to process the record and save to DB.
        }

        //if does not exist

        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            log.error("Invaid URL Argument {}", urlString);
            throw new InvalidArgumentException(new String[]{urlString});
        }

        ExecutorService es = Executors.newFixedThreadPool(2);


        CompletableFuture<Sentiment> pageSentimentFuture = getSentimentAnalysisFuture(url, es);

        CompletableFuture<Document> pageMetadataFuture = getPageMetaDataFuture(urlString, es);

        CompletableFuture<PageData> pageDataFuture = combineResultsAync(pageSentimentFuture, pageMetadataFuture, urlString);

        try {
            PageData pageData = pageDataFuture.get(6000, TimeUnit.MILLISECONDS);

            saveToDatabaseAsync(urlString, pageData);

            return pageData;

        } catch (TimeoutException e) {
            throw new AsyncRequestTimeoutException();
        } catch (InterruptedException |
                ExecutionException e)

        {
            log.error("An Exception Occurred while trying to access Web Page at URL:{}", urlString);
            throw new UnsupportedOperationException();

        }

    }

    private CompletableFuture<PageData> combineResultsAync(CompletableFuture<Sentiment> pageSentimentFuture, CompletableFuture<Document> pageMetadataFuture, String urlString) {
        return pageSentimentFuture
                .thenCombineAsync(pageMetadataFuture, (sentiment, document) -> {
                    PageData pageData = PageUtil.getPageData(document, sentiment, urlString);
                    log.debug("PageData: {}", pageData.toString());
                    return pageData;
                });
    }

    private CompletableFuture<Sentiment> getSentimentAnalysisFuture(URL url, ExecutorService es) {
        return CompletableFuture.supplyAsync(() -> {
                    Sentiment sentiment = sentimentHTTPClient.getSentiment(url);

                    return sentiment != null ? sentiment : new Sentiment();
                }, es

        );
    }

    private CompletableFuture<Document> getPageMetaDataFuture(String urlString, ExecutorService es) {
        return CompletableFuture.supplyAsync(() ->
                {
                    try {
                        return Jsoup.connect(urlString).get();

                    } catch (IOException e) {
                        log.error("An IO Exception occured while parsing data for on Webpage:{}", urlString);
                    }
                    return null;
                }, es
        );
    }

    private void saveToDatabaseAsync(String urlString, PageData pageData) {
        CompletableFuture.runAsync(() ->
                {
                    pageRepository.save(toEntity(pageData));
                    log.info("Saved Into Database, Page Sentiment Data for URL:{} ", urlString);
                }
        );
    }

    public PageData findByUrl(String urlString) {

        PageDataEntity entity = pageRepository.findByUrl(urlString);
        if (entity == null) {
            throw new NoSuchElementException();
        }
        PageData pageData = toDto(entity);

        log.info("Found PageData for URL:{} PageData:{}", urlString, pageData);

        return pageData;
    }
}