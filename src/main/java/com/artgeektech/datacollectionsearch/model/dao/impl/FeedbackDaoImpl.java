package com.artgeektech.datacollectionsearch.model.dao.impl;

import com.artgeektech.datacollectionsearch.model.dao.FeedbackDao;
import com.artgeektech.datacollectionsearch.model.dvo.EsFeedback;
import com.artgeektech.datacollectionsearch.model.dvo.Feedback;
import com.artgeektech.datacollectionsearch.model.repository.FeedbackEsRepository;
import com.artgeektech.datacollectionsearch.model.repository.FeedbackMongoRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class FeedbackDaoImpl implements FeedbackDao {

    @Autowired
    private FeedbackEsRepository esRepository;

    @Autowired
    private FeedbackMongoRepository mongoRepository;

    @Value("${dao.enable-es}")
    private boolean esEnable;

    private static final int DEFAULT_PAGE_SIZE = 20;

    @Override
    public Feedback save(Feedback feedback) {
        Feedback res = mongoRepository.save(feedback);
        if (esEnable) {
            // document's id already assigned by MongoDB
            esRepository.save(new EsFeedback(feedback));
        }
        return res;
    }

    @Override
    public Feedback findById(String id) {
        try {
            return mongoRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public List<Feedback> findByEmail(String email) {
        return mongoRepository.findByEmailOrderByIdDesc(email);
    }

    @Override
    public List<Feedback> search(String content, Integer pageNum, Integer pageSize) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("content", content);
        if (pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0) {
            if (esEnable) {
                return convertToFeedbacks(esRepository.search(matchQuery, PageRequest.of(pageNum - 1, pageSize)));
            } else {
                return mongoRepository.findByContentContainingOrderByIdDesc(content, PageRequest.of(pageNum - 1, pageSize));
            }
        } else {
            if (esEnable) {
                return convertToFeedbacks(esRepository.search(matchQuery, PageRequest.of(0, DEFAULT_PAGE_SIZE)));
            } else {
                return mongoRepository.findByContentContainingOrderByIdDesc(content, PageRequest.of(0, DEFAULT_PAGE_SIZE));
            }
        }
    }

    @Override
    public void deleteById(String id) {
        mongoRepository.deleteById(id);
        if (esEnable) {
            esRepository.deleteById(id);
        }
    }

    private List<Feedback> convertToFeedbacks(Iterable<EsFeedback> esFeedbacks) {
        List<Feedback> res = new ArrayList<>();
        for (EsFeedback esFeedback: esFeedbacks) {
            res.add(new Feedback(esFeedback));
        }
        return res;
    }
}
