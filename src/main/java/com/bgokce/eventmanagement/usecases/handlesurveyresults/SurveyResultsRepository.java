package com.bgokce.eventmanagement.usecases.handlesurveyresults;

import com.bgokce.eventmanagement.usecases.manageevents.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyResultsRepository extends JpaRepository<SurveyResults, Long> {

    @Query("select avg(s.question1), avg(s.question2),avg(s.question3),avg(s.question4)," +
            " avg(s.question5), avg(s.question6), avg(s.question7) from SurveyResults s where s.ownerEvent = :event")
    List<List<Integer>> getSurveyResults(Event event);
}
