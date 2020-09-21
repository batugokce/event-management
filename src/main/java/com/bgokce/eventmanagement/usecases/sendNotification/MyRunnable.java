package com.bgokce.eventmanagement.usecases.sendNotification;

import com.bgokce.eventmanagement.utilities.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class MyRunnable implements Runnable {

    private Long eventId;


    public MyRunnable(Long eventId){
        this.eventId = eventId;
    }


    @SneakyThrows
    @Override
    public void run() {
        NotificationService notificationService = BeanUtil.getBean(NotificationService.class);
        notificationService.sendNotification(this.eventId);
    }
}
