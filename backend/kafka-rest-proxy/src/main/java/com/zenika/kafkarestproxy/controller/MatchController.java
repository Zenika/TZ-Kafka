package com.zenika.kafkarestproxy.controller;

import com.zenika.kafkarestproxy.entity.Match;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController("/match")
public class MatchController {

    @Autowired
    private Consumer<Long,String> receiver;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @GetMapping
    public List<Match> getAllMatch() {
        kafkaTemplate.send("jsontest","toto");
        System.out.println("Read");
        ConsumerRecords<Long, String> records =  receiver.poll(1000);
        System.out.println("Get");
        System.out.println(records.count());
        System.out.println(records.partitions());
        records.iterator().forEachRemaining(record -> {
            System.out.println(record.topic());
            System.out.println(record.value());
        });
        return Collections.emptyList();

    }
}
