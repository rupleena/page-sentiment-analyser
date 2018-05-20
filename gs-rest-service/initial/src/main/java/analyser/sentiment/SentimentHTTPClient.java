package analyser.sentiment;


import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.SentimentParams;
import com.aylien.textapi.responses.Sentiment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@Slf4j
public class SentimentHTTPClient {

    @Autowired
    @Qualifier("sentimentClient")
    TextAPIClient sentimentClient;


    public Sentiment getSentiment(URL url) {
        SentimentParams sentimentParams = SentimentParams.newBuilder().setUrl(url).build();
        try {
            return sentimentClient.sentiment(sentimentParams);
        } catch (TextAPIException e) {
            log.error("No Sentiment Found for URL:{} Exception {}", url.toString(), e.getMessage());
            return null;
        }

    }


}
