package analyser.util;

import analyser.dto.PageData;
import com.aylien.textapi.responses.Sentiment;
import org.jsoup.nodes.Document;

import static analyser.util.DataUtil.UNKNOWN;
import static analyser.util.DataUtil.getString;

public final class PageUtil {

    private PageUtil() {
    }


    public static PageData getPageData(Document document, Sentiment sentiment, String urlString) {

        // Page Document Meta Data
        String pageTitle = document != null ? getString(document.title()) : UNKNOWN;
        String pageDescription = document != null ? getString(document.select("meta[name=description]").first().attr("content")) : UNKNOWN;
        String author = UNKNOWN;

        // Page Sentiment Analysis Data
        String polarity = sentiment != null ? getString(sentiment.getPolarity()) : UNKNOWN;
        String subjectivity = sentiment != null ? getString(sentiment.getSubjectivity()) : UNKNOWN;
        Double subjectivityConfidence = sentiment != null ? sentiment.getSubjectivityConfidence() : null;
        Double polarityConfidence = sentiment != null ? sentiment.getPolarityConfidence() : null;
        String text = sentiment != null ? getString(sentiment.getText()) : UNKNOWN;

        return PageData.builder().url(urlString).pageTitle(pageTitle).description(pageDescription).author(author).polarity(polarity).subjectivity(subjectivity).subjectivityConfidence(subjectivityConfidence).polarityConfidence(polarityConfidence).text(text).build();


    }
}

