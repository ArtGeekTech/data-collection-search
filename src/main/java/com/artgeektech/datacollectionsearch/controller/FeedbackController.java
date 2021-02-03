package com.artgeektech.datacollectionsearch.controller;

import com.artgeektech.datacollectionsearch.model.dao.FeedbackDao;
import com.artgeektech.datacollectionsearch.model.dvo.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FeedbackController {

    @Autowired
    private FeedbackDao feedbackDao;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello " + name;
    }

    @PostMapping("/feedbacks")
    public ResponseEntity<Feedback> create(@RequestBody Feedback feedback) {
        try {
            Feedback res = feedbackDao.save(feedback);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> findById(@PathVariable("id") String id) {
        Feedback res = feedbackDao.findById(id);
        return new ResponseEntity<>(res, res == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/feedbacks/email/{email}")
    public ResponseEntity<List<Feedback>> findByEmail(@PathVariable("email") String email) {
        List<Feedback> res = feedbackDao.findByEmail(email);
        return new ResponseEntity<>(res, res.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> search(
            @RequestParam(name = "content", required = true) String content,
            @RequestParam(name = "pageNum", required = false) Integer pageNum,
            @RequestParam(name = "pageSize", required = false) Integer pageSize
    ) {
        List<Feedback> res = feedbackDao.search(content, pageNum, pageSize);
        return new ResponseEntity<>(res, res.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") String id) {
        try {
            feedbackDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
