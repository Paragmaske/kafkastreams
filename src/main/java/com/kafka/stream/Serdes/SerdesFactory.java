package com.kafka.stream.Serdes;

import com.kafka.stream.model.FraudTransaction;
import com.kafka.stream.model.Transaction;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class SerdesFactory {

    static public Serde<Transaction> transactionSerde() {
        JsonSerializer<Transaction> jsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Transaction> jsonDeserializer = new JsonDeserializer<>(Transaction.class);
        return Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
    }

    static public Serde<FraudTransaction> fraudTransactionSerde() { // Corrected return type
        JsonSerializer<FraudTransaction> jsonSerializer = new JsonSerializer<>();
        JsonDeserializer<FraudTransaction> jsonDeserializer = new JsonDeserializer<>(FraudTransaction.class); // Corrected type
        return Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
    }
}
