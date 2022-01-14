package com.big.assessment.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T extends Object> {
    T entity;
    int code;
    String message;
}
