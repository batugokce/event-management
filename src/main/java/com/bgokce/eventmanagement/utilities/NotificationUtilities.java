package com.bgokce.eventmanagement.utilities;

import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.sendNotification.Notification;
import com.bgokce.eventmanagement.usecases.sendNotification.NotificationData;
import com.bgokce.eventmanagement.usecases.sendNotification.NotificationEntity;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.Set;

public class NotificationUtilities {

    public static NotificationEntity prepareNotification(Set<String> tokens, Event event) {
        String text = event.getEventName() + " isimli etkinlik " + event.getStartDate().toString().replace('T',' ') + " tarihinde başlayacaktır.";
        Notification notification = new Notification("Hatırlatıcı", text);
        NotificationData notificationData = new NotificationData(
                "FLUTTER_NOTIFICATION_CLICK","",""
        );

        NotificationEntity notificationEntity = new NotificationEntity(
                notification,
                "high",
                notificationData,
                tokens
        );
        return notificationEntity;
    }

    public static HttpHeaders prepareHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //TODO; enter your firebase fcm token below
        headers.set("Authorization","Bearer <your-fcm-token>");
        return headers;
    }
}
