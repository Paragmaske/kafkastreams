//package com.kafka.stream;
//
//import com.kafka.stream.model.FraudTransaction;
//import com.kafka.stream.model.Transaction;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.kstream.KStream;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.function.Function;
//
//@Configuration
//public class TransactionKafkaProcessor {
//
//    @Bean
//    public Function<KStream<String, Transaction>, KStream<String, FraudTransaction>> transactionProcessor() {
//        return kstream -> kstream
//                .filter((key, transaction) -> transaction.getTransactionId() % 2 == 0) // Filter for even transaction IDs
//                .map((key, transaction) -> {
//                    // Log the received transaction
//                    System.out.println("Received transaction: " + transaction);
//
//                    // Transform to FraudTransaction
//                    FraudTransaction fraudTransaction = new FraudTransaction(
//                            transaction.getTransactionId(),
//                            transaction.getUserId(),
//                            "Fraud"
//                    );
//                    return new KeyValue<>(key, fraudTransaction);
//                });
//    }
//}
