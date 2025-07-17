package com.manibala.test.groq.mail;

import com.manibala.framework.kafka.ConsumerApp;
import com.manibala.framework.kafka.ProducerApp;
import com.manibala.framework.mail.MailTask;
import com.manibala.framework.util.ActorUtils;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class MailTest {
    @Test
    public void test1() {
        Actor actor = ActorUtils.getActor();
        actor.attemptsTo(MailTask.with());
    }
}
