package com.zenika.technozaure.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class StreamsExercise {

    public static class Prono {
        Long matchId = 0L;
        String userId = "";
        Integer teamA = 0;
        Integer teamB = 0;
    }

    public static class Result {
        Long matchId = 0L;
        Integer teamA = 0;
        Integer teamB = 0;
    }

    public static class Score {
        Long matchId = 0L;
        String userId = "";
        Integer score = 0;
    }

    public static void main(String[] args) throws Exception {
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "score-calculator");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Prono> prono = builder.stream("prono");
        KStream<String, Result> results = builder.stream("results");

        KStream<String, Score> scores = prono.outerJoin(results,
                (pronoValue, resultValue) -> {
                    Score score = new Score();
                    score.score = 0;
                    if (pronoValue.teamA == resultValue.teamA && pronoValue.teamB == resultValue.teamB) {
                        // Score exact
                        score.score = 8;
                    } else if (pronoValue.teamA == pronoValue.teamB && resultValue.teamA == resultValue.teamB) {
                        // Match null sans le bon score
                        score.score = 4;
                    } else if (pronoValue.teamA > pronoValue.teamB && resultValue.teamA > resultValue.teamB) {
                        // Un vainqueur mais sans le bon score
                        score.score = 2;
                    }
                    score.matchId = pronoValue.matchId;
                    score.userId = pronoValue.userId;

                    return score;
                },
                JoinWindows.of(TimeUnit.MINUTES.toMillis(2))
        ).selectKey((s, score) -> score.userId);

        scores.to("score_topic");

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing the streams");
            streams.close();
        }));
    }

}

