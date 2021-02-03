package com.artgeektech.datacollectionsearch.model.repository;

import com.artgeektech.datacollectionsearch.model.dvo.EsFeedback;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackEsRepository extends ElasticsearchRepository<EsFeedback, String> {
}
