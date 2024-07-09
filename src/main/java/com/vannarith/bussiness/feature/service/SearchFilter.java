package com.vannarith.bussiness.feature.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.vannarith.bussiness.feature.user.SearchRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class SearchFilter<T> {
    public Specification<T> getSearchSpecification(SearchRequest searchRequest){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb){
                return cb.equal(root.get(searchRequest.getColumn()), searchRequest.getValue());
            }
        };
    }
}
