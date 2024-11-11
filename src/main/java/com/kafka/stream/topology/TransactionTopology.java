package com.kafka.stream.topology;

import com.kafka.stream.Serdes.SerdesFactory;
import com.kafka.stream.model.FraudTransaction;
import com.kafka.stream.model.Transaction;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Component;

@Component
public class TransactionTopology {

    public static String TRANSACTION_TOPIC = "transaction";
    public static String PROCESSED_TRANSACTION_TOPIC = "fraud_alert";

    public static Topology buildTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        // Use the SerdesFactory to get the Serde for Transaction
        var transactionStream = streamsBuilder.stream(
                TRANSACTION_TOPIC,
                Consumed.with(Serdes.String(), SerdesFactory.transactionSerde())
        );

        transactionStream.print(Printed.<String, Transaction>toSysOut().withLabel("transactionStream"));

        // Filter and transform to FraudTransaction
        var processedStream = transactionStream
                .filter((key, transaction) -> transaction.getAmount() > 100) // Example criteria
                .mapValues(transaction -> {
                    // Create FraudTransaction from Transaction
                    return FraudTransaction.builder()
                            .transactionId(transaction.getTransactionId())
                            .userId(transaction.getUserId())
                            .message("Fraud detected!")
                            .build();
                });

        processedStream.print(Printed.<String, FraudTransaction>toSysOut().withLabel("processedTransactionStream"));

        // Use the SerdesFactory to get the Serde for FraudTransaction
        processedStream.to(
                PROCESSED_TRANSACTION_TOPIC,
                Produced.with(Serdes.String(), SerdesFactory.fraudTransactionSerde()) // Ensure you have a serde for FraudTransaction
        );

        return streamsBuilder.build();
    }
}
