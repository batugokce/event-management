package com.bgokce.eventmanagement.usecases.manageattending;

import com.bgokce.eventmanagement.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "answer_seq")
public class Answer extends BaseEntity {
    private String answer1;
    private String answer2;
    private String answer3;

    public Answer(List<String> list){
        this.answer1 = list.get(0);
        this.answer2 = list.get(1);
        this.answer3 = list.get(2);
    }
}
