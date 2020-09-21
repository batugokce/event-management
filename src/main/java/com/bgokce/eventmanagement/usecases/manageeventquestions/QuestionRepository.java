package com.bgokce.eventmanagement.usecases.manageeventquestions;

import com.bgokce.eventmanagement.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
