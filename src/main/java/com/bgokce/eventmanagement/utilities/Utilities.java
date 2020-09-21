package com.bgokce.eventmanagement.utilities;

import com.bgokce.eventmanagement.usecases.manageattending.Answer;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.managepersonquestions.PersonQuestion;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class Utilities {
    private static final String QR_CODE_IMAGE_PATH = "./qrcode.png";

    public static String generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();

        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200, hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        String s = Base64.getEncoder().encodeToString(pngData);


        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return s;

    }

    public static String getAttendingQRCode(Event event, Person person) throws Exception {
        String s = new JSONObject()
                .put("Etkinlik İsmi: ",event.getEventName())
                .put("Etkinlik Yeri: ", event.getCity())
                .put("Etkinlik Başlangıç Tarihi: ", event.getStartDate())
                .put("Etkinlik Bitiş Tarihi: ", event.getEndDate())
                .put("Katılımcı Adı-Soyadı: ", person.getNameSurname())
                .put("Katılımcı TC Kimlik No: ", person.getTcNo())
                .toString();

        return generateQRCodeImage(s);
    }

    public static EventPerson prepareEventPerson(Event event, Person person, Answer answer){
        EventPerson eventPerson = new EventPerson();
        eventPerson.setEvent(event);
        eventPerson.setPerson(person);
        eventPerson.setEventId(event.getId());
        eventPerson.setTcNo(person.getTcNo());
        eventPerson.setAnswer(answer);
        eventPerson.setIsCompletedSurvey(false);
        return eventPerson;
    }


    public static Map<Long, Set<PersonQuestion>> getQuestionMapping(Set<EventPerson> participation, Set<Event> allEvents) {
        Map<Long,Set<PersonQuestion>> map = new HashMap<>();
        allEvents.forEach(item -> map.put(item.getId(),new HashSet<>()));
        participation.forEach(item -> map.get(item.getEventId()).addAll(item.getUsersQuestions()) );

        return map;
    }

    public static Map<Long, List> getQuestionAndSurveyCompletionMapping(Set<EventPerson> participation, Set<Event> allEvents) {
        Map<Long,List> map = new HashMap<>();
        participation.forEach(item -> map.put(item.getEventId(),List.of(item.getUsersQuestions(),item.getIsCompletedSurvey())));
        allEvents.forEach(item -> map.putIfAbsent(item.getId(),List.of(new HashSet<>(),false)));
        return map;
    }
}
