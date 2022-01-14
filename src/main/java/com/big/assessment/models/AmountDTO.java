package com.big.assessment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountDTO implements Serializable {
    private Integer toUserId;
    private Double amount;
}
