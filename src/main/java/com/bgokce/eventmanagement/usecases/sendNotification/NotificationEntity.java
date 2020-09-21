package com.bgokce.eventmanagement.usecases.sendNotification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {
    private Notification notification;
    private String priority;
    private NotificationData notificationData;
    private Set<String> registration_ids;
}
