package com.soen343.shs.dal.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class RealUser extends User {
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @OneToMany
    private Set<House> home;
}
