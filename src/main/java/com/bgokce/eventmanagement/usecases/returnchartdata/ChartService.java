package com.bgokce.eventmanagement.usecases.returnchartdata;

import com.bgokce.eventmanagement.usecases.manageattending.EventPersonRepository;
import com.bgokce.eventmanagement.usecases.manageevents.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChartService {

    private final EventRepository eventRepository;
    private final EventPersonRepository eventPersonRepository;

    public Map<String,Long> getEventsAndNumberOfParticipants(){
        Map<String,Long> result = new HashMap<>();
        List<String> eventNames = eventRepository.getEventNames();
        eventNames.stream().forEach(item -> result.put(item,0L));
        List<List<Object>> obj =  eventPersonRepository.getDataForTheFirstChart();
        obj.stream().forEach(item -> result.put((String)item.get(0),(Long) item.get(1)));

        return result;
    }

    public Object getDaysOfApplications(Long id) {
        List<List<Object>> list = eventPersonRepository.GetDaysOfApplications(id);
        return list;
    }
}
