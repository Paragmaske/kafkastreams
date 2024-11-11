package com.kafka.stream.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.kafka.stream.model.PaymentStatus;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Transaction {


    private Long transactionId;
    private String userId;
    private String location;
    private double amount;
    private LocalDateTime timestamp;
    private PaymentStatus paymentStatus;
}
