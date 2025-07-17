package com.manibala.framework.kafka;

import com.manibala.application.groq.api.config.ConfigProperties;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.time.Duration;
import java.util.*;

public class ConsumerApp {
    public static void read(Actor actor) {
        actor.attemptsTo(Task.where("Read message from Kafka topic "+ConfigProperties.getKafkaTopic(), new Performable() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(ConsumerProperties.get());
                consumer.subscribe(Collections.singleton(ConfigProperties.getKafkaTopic()));
                while (true) {
                    System.out.println("I'm here inside loop Consumer");
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    records.forEach(r -> System.out.printf("Consumed %s = %s%n", r.key(), r.value()));
                    System.out.println("I'm here inside loop Consumer done");
                    break;
                }
            }
        }));
    }
}