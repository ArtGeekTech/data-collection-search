package com.artgeektech.datacollectionsearch.model.dao;

import com.artgeektech.datacollectionsearch.model.dvo.Feedback;

import java.util.List;


public interface FeedbackDao {
    Feedback save(Feedback feedback);
    Feedback findById(String id);
    List<Feedback> findByEmail(String email);
    List<Feedback> search(String content, Integer pageNum, Integer pageSize);
    void deleteById(String id);
}
