package com.bgokce.eventmanagement.usecases.sendNotification;

import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonRepository;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageevents.EventRepository;
import com.bgokce.eventmanagement.utilities.NotificationUtilities;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EventPersonRepository eventPersonRepository;
    private final EventRepository eventRepository;
    private TaskScheduler taskScheduler;

    @Async
    public Boolean setNotification(Long startDateTimestamp, Long eventId) {
        Date now = new Date();
        Long notificationTime = startDateTimestamp - 1000*60*60;
        Date notificationDate = new Date(notificationTime);
        if (now.after(notificationDate)){
            return false;
        }

        Runnable newRunnable = new MyRunnable(eventId);
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        taskScheduler = new ConcurrentTaskScheduler(localExecutor);

        taskScheduler.schedule(newRunnable, notificationDate);
        return true;
    }

    public MessageResponse sendNotification(Long eventId) throws URISyntaxException {
        Set<String> tokens = eventPersonRepository.getFirebaseTokensOfParticipants(eventId);
        Set<String> filteredTokens = tokens.stream().filter(item -> item.length() != 0).collect(Collectors.toSet());
        if (filteredTokens.size() == 0){
            return new MessageResponse(ResponseMessages.WARNING,ResponseMessages.MOBILE_USER_NOT_FOUND,null);
        }
        Event event = eventRepository.findById(eventId).orElse(null);

        int nOfReceivers = filteredTokens.size();
        RestTemplate restTemplate = new RestTemplate();
        final String fcmBaseUrl = "https://fcm.googleapis.com/fcm/send";
        URI uri = new URI(fcmBaseUrl);

        NotificationEntity notificationEntity = NotificationUtilities.prepareNotification(filteredTokens,event);

        HttpHeaders headers = NotificationUtilities.prepareHeaders();
        HttpEntity<NotificationEntity> request = new HttpEntity<>(notificationEntity,headers);

        restTemplate.postForEntity(uri,request,String.class);
        return new MessageResponse(ResponseMessages.SUCCESS, "Etkinliğe kayıtlı " + nOfReceivers + " kullanıcıya bildirim gönderildi", null);
    }
}
