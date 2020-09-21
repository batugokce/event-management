package com.bgokce.eventmanagement.usecases.returnchartdata;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/admin/firstChart")
    public Map<String,Long> returnDataForTheFirstChart(){
        return chartService.getEventsAndNumberOfParticipants();
    }

    @GetMapping("/admin/secondChart/{id}")
    public Object returnDataForTheSecondChart(@PathVariable Long id){
        return chartService.getDaysOfApplications(id);
    }
}
