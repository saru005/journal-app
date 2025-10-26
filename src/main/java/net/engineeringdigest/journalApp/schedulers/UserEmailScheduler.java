package net.engineeringdigest.journalApp.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserEmailScheduler {

//    @Scheduled(cron = "0 * * * * *")
//    @Scheduled(cron = "*/2 * * * * *")
    public void sendEmailToUsers(){
        System.out.println("Mail Sent successfully..");
    }
}
