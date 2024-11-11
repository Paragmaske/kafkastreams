package com.kafka.stream.launcher;

import com.kafka.stream.topology.StreamTopology;
import com.kafka.stream.topology.TransactionTopology;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class GreetingsStreamApp {

    public static void main(String[] args) {
Properties properties=new Properties();
properties.put("bootstrap.servers", "localhost:9092");
properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams");
properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
createTopics(properties,List.of(StreamTopology.GREETING,StreamTopology.GREETING_UPPERCASE));
      var greetingsTopology=  StreamTopology.buildTopology();
      var kafkaStreams=new KafkaStreams(greetingsTopology, properties);
kafkaStreams.start();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "transaction-streams"); // Unique ID for transactions
        var transactionTopology = TransactionTopology.buildTopology();
        var kafkaStreamsTransactions = new KafkaStreams(transactionTopology, properties);
        kafkaStreamsTransactions.start();
    }

    private static void createTopics(Properties config, List<String> greetings) {

        AdminClient admin = AdminClient.create(config);
        var partitions = 1;
        short replication  = 1;

        var newTopics = greetings
                .stream()
                .map(topic ->{
                    return new NewTopic(topic, partitions, replication);
                })
                .collect(Collectors.toList());

        var createTopicResult = admin.createTopics(newTopics);
        try {
            createTopicResult
                    .all().get();
            System.out.println("topics are created successfully");
        } catch (Exception e) {
            System.out.println("Exception creating topics");
        }
    }
}