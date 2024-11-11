package com.kafka.stream.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class FraudTransaction {

    private Long transactionId;
    private String userId;
    private  String message;
}
