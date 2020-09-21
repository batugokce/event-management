package com.bgokce.eventmanagement.usecases.manageattending;

import com.bgokce.eventmanagement.usecases.manageperson.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EventPersonRepository extends JpaRepository<EventPerson, EventPersonPK> {

    @Query("select b.eventName, count(e.eventId) from EventPerson as e inner join e.event as b " +
            "                                    where e.eventId = b.id GROUP BY e.eventId, b.id")
    List<List<Object>> getDataForTheFirstChart();

    @Query("select e.created, count(e.created) from EventPerson as e " +
            "                                  where e.eventId = :id GROUP BY e.created")
    List<List<Object>> GetDaysOfApplications(Long id);

    @Query("from EventPerson as ep left join fetch ep.person " +
            "                      left join fetch ep.event " +
            "where ep.eventId = :id and ep.tcNo = :tc")
    EventPerson getAttending(Long id, String tc);

    @Query(" select ep " +
            "from EventPerson ep left join fetch ep.usersQuestions " +
            "                    left join fetch ep.event as e " +
            "                    join fetch ep.person " +
            "                    left join fetch e.question " +
            "where ep.tcNo = :tc")
    Set<EventPerson> getAttendingForPerson(String tc);

    @Query("select ep " +
            "from EventPerson ep left join fetch ep.usersQuestions" +
            "                    left join fetch ep.event e " +
            "                    left join fetch ep.person p" +
            "                    left join fetch e.question")
    Set<EventPerson> getAttendingForAdmin();


    @Query("select ep.person from EventPerson ep where ep.eventId = :id")
    Set<Person> getPeopleAttendedTheEvent(Long id);

    @Query("select ep.person.firebaseToken from EventPerson ep where ep.eventId = :id")
    Set<String> getFirebaseTokensOfParticipants(Long id);

}
