package com.artgeektech.datacollectionsearch.model.dvo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "feedback")
//@Setting(settingPath = "/Users/agt/Development/data-collection-search/src/main/java/com/artgeektech/datacollectionsearch/config/elastic-analyzer.json")
public class EsFeedback {

    @Id
    private String id;

//    @Field(type = FieldType.Keyword)
    private String email;

//    @Field(type = FieldType.Text, analyzer = "english_analyzer_v1", searchAnalyzer = "english_search_analyzer_v1")
    private String content;

    public EsFeedback() {}

    public EsFeedback(Feedback feedback) {
        this.id = feedback.getId();
        this.email = feedback.getEmail();
        this.content = feedback.getContent();
    }
    public EsFeedback(String email, String content) {
        this.email = email;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
