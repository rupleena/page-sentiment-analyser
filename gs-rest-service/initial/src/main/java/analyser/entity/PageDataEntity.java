package analyser.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PageDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "url")
    private String url;

    @Lob
    @Column(name = "page_title", length = 10000)
    private String pageTitle;

    @Lob
    @Column(name = "description", length = 500000)
    private String description;

    @Column(name = "author")
    private String author;

    @Column(name = "polarity")
    private String polarity;

    @Column(name = "subjectivity")
    private String subjectivity;

    @Lob
    @Column(name = "text", length = 500000)
    private String text;

    @Column(name = "polarity_confidence")
    private Double polarityConfidence;

    @Column(name = "subjectivity_confidence")
    private Double subjectivityConfidence;
}
