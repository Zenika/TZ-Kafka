package com.zenika.technozaure.kafka;

public class SerializerTest {
    public static void main(String... args) {
        JsonPOJOSerializer<StreamsExercise.Match> matchSerializer = new JsonPOJOSerializer<>();
        JsonPOJOSerializer<StreamsExercise.Prono> pronoSerializer = new JsonPOJOSerializer<>();
        JsonPOJOSerializer<StreamsExercise.Result> resultSerializer = new JsonPOJOSerializer<>();
        System.out.println(new String(matchSerializer.serialize("test", new StreamsExercise.Match())));
        System.out.println(new String(pronoSerializer.serialize("test", new StreamsExercise.Prono())));
        System.out.println(new String(resultSerializer.serialize("test", new StreamsExercise.Result())));
    }
}
