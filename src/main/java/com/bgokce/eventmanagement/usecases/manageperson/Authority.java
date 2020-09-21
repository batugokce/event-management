package com.bgokce.eventmanagement.usecases.manageperson;

import com.bgokce.eventmanagement.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "auth_seq")
public class Authority extends BaseEntity implements GrantedAuthority {
    private String authority;
}
