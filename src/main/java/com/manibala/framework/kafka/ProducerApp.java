package com.manibala.framework.kafka;

import com.manibala.application.groq.api.config.ConfigProperties;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class ProducerApp {
    public static void send(Actor actor, String message) {
        actor.attemptsTo(Task.where("Publish message to Kafka topic "+ConfigProperties.getKafkaTopic(), new Performable() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                KafkaProducer<String, String> producer = new KafkaProducer<>(ProducerProperties.get());
                for (int i = 1; i <= 1; i++) {
                    System.out.println("I'm here inside loop Producer");
                    ProducerRecord<String, String> record = new ProducerRecord<>(ConfigProperties.getKafkaTopic(), message + " : " + i);
                    producer.send(record);
                    System.out.println("I'm here inside loop Producer done");
                }
            }})
        );
    }
}
