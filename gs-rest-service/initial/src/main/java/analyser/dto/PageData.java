package analyser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageData {

    private String pageTitle;
    private String description;
    private String author;
    private String polarity;
    private String subjectivity;
    private String text;
    private Double polarityConfidence;
    private Double subjectivityConfidence;
    private String url;
}
