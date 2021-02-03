package com.artgeektech.datacollectionsearch.model.dvo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback")
public class Feedback {

    @Id
    private String id;

    @Indexed(name = "email_mongo_index")
    private String email;

    private String content;

    public Feedback() {}

    public Feedback(EsFeedback esFeedback) {
        this.id = esFeedback.getId();
        this.email = esFeedback.getEmail();
        this.content = esFeedback.getContent();
    }
    public Feedback(String email, String content) {
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
