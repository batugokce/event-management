package com.bgokce.eventmanagement.usecases.manageevents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e.eventName from Event as e")
    List<String> getEventNames();

    @Query("select e from Event e left join fetch e.attendedPeople ep" +
            "                     left join fetch e.question " +
            "                     left join fetch ep.person")
    Set<Event> getAllEventsWithPeople();

    @Query("select e from Event e left join fetch e.attendedPeople ep " +
            "                     left join fetch ep.person " +
            "where e.id = :id")
    Event getEventByIdWithPeople(Long id);

    @Query("select e from Event e left join fetch e.attendedPeople ep " +
            "                     left join fetch ep.person " +
            "                     left join fetch e.surveyResults " +
            "where e.id = :id")
    Event getEventByIdWithPeopleAndSurveyResults(Long id);

}
