package com.artgeektech.datacollectionsearch;

import com.artgeektech.datacollectionsearch.model.dvo.Feedback;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataCollectionSearchApplicationTests {

    @LocalServerPort
    private int port;

    private final String host = "http://localhost";

    @Autowired
    private TestRestTemplate restTemplate;

    private String[] testContents = {
            "",
            "sfjoifjoe fsofjoifjs djfodjfwo",
            "The quick brown fox jumps over the lazy dog",
            "A quick brown fox jumps over a lazy dog",
            "tHe qUiCk bRoWn fOx jUmPs oVeR ThE LaZy dOg",
            "The quick brown fox jumped over the lazy dog",
            "The quick brown fox is jumping over the lazy dog",
            "The quick-brown-fox jumps over the lazy dog",
            "The brown fox quickly jumps over the lazy dog",
            "The quick brown foxes jump over the lazy dogs",
            "The lazy dog is jumped over by quick brown fox",
            "fox The quick jumps the lazy over brown dog",
            "The quick brown jumps over the lazy dog",
            "The quick brown fox the lazy dog",
            "foxes jump over dogs",
            "fox jump over dog",
    };


    @BeforeEach
    public void setUp() {
        for (int i = 0; i < testContents.length; i++) {
            Feedback payload = new Feedback("test@gmail.com", testContents[i]);
            testFeedbacks[i] = restTemplate.postForObject(formUrl("/feedbacks"), payload, Feedback.class);
        }
    }

    @AfterEach
    public void cleanUp() {
        for (int i = 0; i < testContents.length; i++) {
            restTemplate.delete(formUrl("/feedbacks/" + testFeedbacks[i].getId()), "");
        }
    }

    @Test
    public void testCrudApi() {
        // test POST API
        final String email = "test0@gmail.com";
        final String content = "djfosd djfowjfow jfosdjf";
        Feedback payload = new Feedback(email, content);
        ResponseEntity<Feedback> response1 = restTemplate.postForEntity(formUrl("/feedbacks"), payload, Feedback.class);
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        final String id = response1.getBody().getId();
        assertNotNull(id);

        // test findDataById
        ResponseEntity<Feedback> response2 = restTemplate.getForEntity(formUrl("/feedbacks/" + id), Feedback.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        Feedback response2Body = response2.getBody();
        assertEquals(id, response2Body.getId());
        assertEquals(email, response2Body.getEmail());
        assertEquals(content, response2Body.getContent());

        // test DELETE API
        restTemplate.delete(formUrl("/feedbacks/" + id), "");
        ResponseEntity<Feedback> response3 = restTemplate.getForEntity(formUrl("/feedbacks/" + id), Feedback.class);
        assertEquals(HttpStatus.NOT_FOUND, response3.getStatusCode());
        assertNull(response3.getBody());
    }

    @Test
    public void testFindByEmail() {
        final String email = "test@gmail.com";
        ResponseEntity<List> response = restTemplate.getForEntity(formUrl("/feedbacks/email/" + email), List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Feedback> feedbackList = response.getBody();
        assertEquals(testContents.length, feedbackList.size());
        System.out.println(formOutputStr(feedbackList.toArray()));
    }

    @Test
    public void testSearchApiCasePending() {
        String[] searchContents = {
                "fox",
                "dog",
                "jump",
                "fox jump dog",
                "fo jum do",
                "laoliu",
                ""
        };
        List<Feedback> output;
        Map<String, String> urlVariables = new HashMap<>();

        System.out.println("All Test Data: ");
        System.out.println(formOutputStr(testContents));

        for (String content: searchContents) {
            urlVariables.put("content", content);
            output = restTemplate.getForObject(formUrl("/feedbacks?content={content}"), List.class, urlVariables);
            System.out.println("\nSearching: " + content);
            if (output == null) {
                System.out.println(0+ "/" + testContents.length);
            } else {
                System.out.println(output.size() + "/" + testContents.length);
                System.out.println(formOutputStr(output.toArray()));
            }
        }
    }

    private Feedback[] testFeedbacks = new Feedback[testContents.length];

    private String formUrl(String path) {
        return host + ":" + port + "/api/v1" + path;
    }

    private String formOutputStr(Object[] arr) {
        String[] strArr = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            strArr[i] = arr[i].toString() + "\n";
        }
        return String.join("\n", strArr);
    }
}
