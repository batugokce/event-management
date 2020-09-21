package com.bgokce.eventmanagement.usecases.managepersonquestions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonQuestionRepository extends JpaRepository<PersonQuestion, Long> {

    @Query("select pq from PersonQuestion pq join fetch pq.ownerAttending ep " +
            "                                join fetch ep.person " +
            "                                join fetch ep.event " +
            "where pq.id = :questionId")
    PersonQuestion getByIdWithPerson(Long questionId);

}
