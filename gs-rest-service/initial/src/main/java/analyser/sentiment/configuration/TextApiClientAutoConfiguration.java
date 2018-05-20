package analyser.sentiment.configuration;

import com.aylien.textapi.TextAPIClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextApiClientAutoConfiguration {

    private static final String APP_ID = "3a38cbf2";
    private static final String APP_KEY = "fd08d31ea754dd44148c155d329b8dab";

    @Bean(name = "sentimentClient")
    public TextAPIClient getClient() {
        return new TextAPIClient(APP_ID, APP_KEY);
    }

}
