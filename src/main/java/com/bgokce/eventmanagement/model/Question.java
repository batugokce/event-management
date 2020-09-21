package com.bgokce.eventmanagement.model;

import com.bgokce.eventmanagement.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "question_seq")
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseEntity {

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/

    private String q1;
    private String q2;
    private String q3;
    private int counter;

    public Question(List<String> list){
        this.q1 = list.get(0);
        this.q2 = list.get(1);
        this.q3 = list.get(2);
        this.counter = (int) list.stream().filter(item -> item.length()!=0).count();
    }
}
