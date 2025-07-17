package com.manibala.framework.mail;

import com.manibala.framework.ui.UiPojo;
import com.manibala.framework.util.LogUtils;
import jakarta.mail.*;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.util.Properties;

public class MailTask implements Task {

    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            System.setProperty("mail.imaps.ssl.protocols", "TLSv1.2");
            System.setProperty("mail.imaps.ssl.enable", "true");
            System.out.println("1");
                String host = "outlook.office365.com"; // For Outlook
                String username = "manikandan.vijaybala@gmail.com";
                String password = "thotathellam!vetri@"; // Use App Password if MFA is enabled
            System.out.println("2");
                // 1. Setup properties
                Properties props = new Properties();
                props.put("mail.store.protocol", "imaps");
                props.put("mail.imaps.host", host);
                props.put("mail.imaps.port", "993");
                props.put("mail.imaps.ssl.enable", "true");
            System.out.println("3");
                // 2. Connect to IMAP
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            System.out.println("3A");
                Store store = session.getStore(); //session.getStore("imaps");
                store.connect(host, username, password);
            System.out.println("4");
                System.out.println(store.isConnected());
                // 3. Open INBOX folder
                Folder inbox = store.getFolder("Inbox");
                inbox.open(Folder.READ_ONLY);
            System.out.println("5");
                // 4. Read messages
                Message[] messages = inbox.getMessages();
                System.out.println("Total Messages: " + messages.length);
            System.out.println("6");
                for (int i = 0; i < Math.min(5, messages.length); i++) {
                    Message msg = messages[i];
                    System.out.println("---------------------------");
                    System.out.println("Subject: " + msg.getSubject());
                    System.out.println("From: " + msg.getFrom()[0]);
                    System.out.println("Sent Date: " + msg.getSentDate());
                    System.out.println("Content: " + msg.getContent().toString());
                }
            System.out.println("7");
                // 5. Close connection
                inbox.close(false);
                store.close();
            System.out.println("8");
        } catch (Exception e) {
            LogUtils.fail(actor, "Failed when "+flag+" - "+e.getMessage());
        }
    }

    public MailTask(String flag) {
        this.flag = flag;
    }

   public static MailTask with() {
        return Tasks.instrumented(MailTask.class, "Mail");
    }
}