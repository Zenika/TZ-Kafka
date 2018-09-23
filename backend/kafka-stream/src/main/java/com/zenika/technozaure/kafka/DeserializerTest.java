package com.zenika.technozaure.kafka;

import java.util.HashMap;
import java.util.Map;

public class DeserializerTest {
    public static void main(String... args) {
        Map<String, Object> serdeProps = new HashMap<>();

        JsonPOJODeserializer<StreamsExercise.Match> matchDes = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", StreamsExercise.Match.class);
        matchDes.configure(serdeProps, false);

        JsonPOJODeserializer<StreamsExercise.Prono> pronoDes = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", StreamsExercise.Prono.class);
        pronoDes.configure(serdeProps, false);

        JsonPOJODeserializer<StreamsExercise.Result> resultDes = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", StreamsExercise.Result.class);
        resultDes.configure(serdeProps, false);

        System.out.println(matchDes.deserialize("test", "{\"matchId\":null,\"teamA\":null,\"teamB\":null,\"date\":null}".getBytes()));
        System.out.println(pronoDes.deserialize("test", "{\"match\":null,\"user\":null,\"scoreHome\":null,\"scoreAway\":null,\"date\":null}".getBytes()));
        System.out.println(pronoDes.deserialize("test", "{\"match\":{\"matchId\":null,\"teamA\":null,\"teamB\":null,\"date\":null},\"user\":null,\"scoreHome\":null,\"scoreAway\":null,\"date\":null}".getBytes()).getMatch());
        System.out.println(resultDes.deserialize("test", "{\"teamA\":0,\"teamB\":0}".getBytes()));
    }
}
