package com.artgeektech.datacollectionsearch.model.repository;

import com.artgeektech.datacollectionsearch.model.dvo.Feedback;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackMongoRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findByEmailOrderByIdDesc(String email);
    List<Feedback> findByContentContainingOrderByIdDesc(String content, Pageable pageable);  //  "bug" ==> SELECT * ... LIKE %bug%
}
