package com.bgokce.eventmanagement.usecases.manageperson;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface PersonRepository extends JpaRepository<Person, String > {

    Person findByTcNo(String tcNo);

    Person findByUsername(String username);

    @Cacheable(value = "person", key = "#username")
    @Query("select p from Person p left join fetch p.authorities where p.username = :username")
    Person findByUserNameWithAuthorities(String username);

    @Query("select p from Person p left join fetch p.attendings ep " +
            "                      left join fetch ep.event e left join fetch ep.person people " +
            "where p.tcNo = :tcNo")
    Person findByTcNoWithEventPerson(String tcNo);

    Boolean existsByUsername(String username);

    @Modifying
    @Transactional
    @Query("update Person p set p.firebaseToken = :firebaseToken where p.username = :username")
    void setFirebaseToken(String username, String firebaseToken);

}
