package com.zenika.technozaure.kafka;

import junit.framework.Test;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class StreamsExercise {

    /**
     * {
     *   "teamA":"Bordeaux",
     *   "teamB":"Lille",
     *   "date":"26/09/2018 19:00"
     * }
     */
    public static class Match {
        private String matchId;
        private String teamA;
        private String teamB;
        private String date;

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public String getTeamA() {
            return teamA;
        }

        public void setTeamA(String teamA) {
            this.teamA = teamA;
        }

        public String getTeamB() {
            return teamB;
        }

        public void setTeamB(String teamB) {
            this.teamB = teamB;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    /**
     * {
     *   "username":"lhauspie"
     * }
     */
    public static class User {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    /**
     * 0  {
     * 1    "match": {
     * 2      "matchId": "monaco_angers",
     * 3      "teamA": "Monaco",
     * 4      "teamB": "Angers",
     * 5      "date": "25/09/2018 9:00"
     * 6    },
     * 7    "scoreHome": 0,
     * 8    "scoreAway": 0,
     * 9    "user": {
     * 10     "username": "aaa"
     * 11   },
     * 12   "date": "23/09/2018 22:29:28"
     * 13 }
     */
    public static class Prono {
        private Match match;
        private User user;
        private Integer scoreHome;
        private Integer scoreAway;
        private String date;

        public Match getMatch() {
            return match;
        }

        public void setMatch(Match match) {
            this.match = match;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Integer getScoreHome() {
            return scoreHome;
        }

        public void setScoreHome(Integer scoreHome) {
            this.scoreHome = scoreHome;
        }

        public Integer getScoreAway() {
            return scoreAway;
        }

        public void setScoreAway(Integer scoreAway) {
            this.scoreAway = scoreAway;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }


    public static class Result {
        private Integer teamA = 0;
        private Integer teamB = 0;

        public Integer getTeamA() {
            return teamA;
        }

        public void setTeamA(Integer teamA) {
            this.teamA = teamA;
        }

        public Integer getTeamB() {
            return teamB;
        }

        public void setTeamB(Integer teamB) {
            this.teamB = teamB;
        }
    }

    /**
     * {
     *   matchId: record.key,
     *   score: record.value.score,
     *   userId: record.value.userId
     * }
     */
    public static class Score {
        private Integer score = 0;
        private String userId = "";

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> serdeProps = new HashMap<>();

        JsonPOJODeserializer<Prono> pronoDes = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", Prono.class);
        pronoDes.configure(serdeProps, false);
        Serde<Prono> pronoSerdes = Serdes.serdeFrom(new JsonPOJOSerializer<>(), pronoDes);

        JsonPOJODeserializer<Result> resultDes = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", Result.class);
        resultDes.configure(serdeProps, false);
        Serde<Result> resultSerdes = Serdes.serdeFrom(new JsonPOJOSerializer<>(), resultDes);

        JsonPOJODeserializer<Score> scoreDes = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", Score.class);
        scoreDes.configure(serdeProps, false);
        Serde<Score> scoreSerdes = Serdes.serdeFrom(new JsonPOJOSerializer<>(), scoreDes);


        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "score-calculator");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder builder = new StreamsBuilder();

//        KStream<String, Prono> prono = builder.stream("prono", Consumed.with(Serdes.String(), pronoSerdes));
//        prono.to("prono_bis", Produced.with(Serdes.String(), pronoSerdes));

//        KStream<String, Result> results = builder.stream("results", Consumed.with(Serdes.String(), resultSerdes));
//        results.to("results_bis", Produced.with(Serdes.String(), resultSerdes));

        KStream<String, String> pronoStr = builder.stream("prono", Consumed.with(Serdes.String(), Serdes.String()));
        KStream<String, String> resultsStr = builder.stream("results", Consumed.with(Serdes.String(), Serdes.String()));

        KStream<String, Score> scores = pronoStr.outerJoin(resultsStr, (pronoVal, resultVal) -> {
            System.out.printf("joined with %s and %s\n", pronoVal, resultVal);
            if (pronoVal == null || resultVal == null) {
                return null;
            }
            Prono prono = pronoDes.deserialize("prono", pronoVal.getBytes());
            Result result = resultDes.deserialize("results", resultVal.getBytes());
            Score score = new Score();
            if (prono.scoreHome == result.teamA && prono.scoreAway == result.teamB) {
                // Score exact
                score.score = 8;
            } else if (prono.scoreHome == prono.scoreAway && result.teamA == result.teamB) {
                // Match null sans le bon score
                score.score = 4;
            } else if (prono.scoreHome > prono.scoreAway && result.teamA > result.teamB
                    || prono.scoreHome < prono.scoreAway && result.teamA < result.teamB) {
                // Un vainqueur mais sans le bon score
                score.score = 2;
            }
            score.userId = prono.user.username;
            return score;
        }, JoinWindows.of(TimeUnit.MINUTES.toMillis(10)));
//
//        KStream<String, Score> scores = prono.join(results,
//                (pronoValue, resultValue) -> {
//                    Score score = new Score();
//                    score.score = 0;
//                    if (pronoValue.scoreHome == resultValue.teamA && pronoValue.scoreAway == resultValue.teamB) {
//                        // Score exact
//                        score.score = 8;
//                    } else if (pronoValue.scoreHome == pronoValue.scoreAway && resultValue.teamA == resultValue.teamB) {
//                        // Match null sans le bon score
//                        score.score = 4;
//                    } else if (pronoValue.scoreHome > pronoValue.scoreAway && resultValue.teamA > resultValue.teamB
//                            || pronoValue.scoreHome < pronoValue.scoreAway && resultValue.teamA < resultValue.teamB) {
//                        // Un vainqueur mais sans le bon score
//                        score.score = 2;
//                    }
//                    score.userId = pronoValue.user.username;
//
//                    return score;
//                },
//                JoinWindows.of(TimeUnit.MINUTES.toMillis(2))
//        );
//
        scores.to("score", Produced.with(Serdes.String(), scoreSerdes));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing the streams");
            streams.close();
        }));
    }

}

