package com.vannarith.bussiness.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

import lombok.AccessLevel;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchRequestDTO {

    List<SearchRequest> searchRequest;
    public enum GlobalOperator {
        OR, AND
    }
}
