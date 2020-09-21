package com.bgokce.eventmanagement.usecases.sendNotification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationData {
    private String click_action;
    private String id;
    private String status;
}
