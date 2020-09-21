package com.bgokce.eventmanagement.usecases.manageperson;

import com.bgokce.eventmanagement.common.BaseEntity;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.validator.TCKimlikNo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "tcNo")*/
@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Person extends BaseEntity implements UserDetails, CredentialsContainer {


    @TCKimlikNo
    @Column(name = "TC", unique = true)
    private String tcNo;

    @NotBlank(message = "Kullanıcı adı boş olamaz.")
    @Column(name = "USERNAME", unique = true)
    private String username;

    @NotBlank(message = "Şifre boş olamaz.")
    @Column(name = "PASSWORD")
    private String password;

    @Email(message = "E-mail formatı uygun değil.")
    @Column(name = "EMAIL")
    private String email;

    @NotBlank(message = "Ad-soyad boş bırakılamaz.")
    @Column(name = "NAME_SURNAME")
    private String nameSurname;

    private String firebaseToken = "";

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private Set<EventPerson> attendings = new HashSet<EventPerson>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_AUTHORITIES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID"))
    private Set<Authority> authorities = new HashSet<>();



    @Override
    public void eraseCredentials() {

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
