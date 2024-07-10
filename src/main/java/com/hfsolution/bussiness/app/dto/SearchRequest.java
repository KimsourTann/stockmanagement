package com.hfsolution.bussiness.app.dto;

import com.hfsolution.bussiness.app.Enum.Operator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchRequest {
    String column;
    String value;
    String joinTable;
    Operator operator = Operator.EQUAL;
}
