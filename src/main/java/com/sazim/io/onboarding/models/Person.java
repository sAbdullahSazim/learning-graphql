package com.sazim.io.onboarding.models;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Person extends AbstractPersistable<Long> {
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", orphanRemoval = true, fetch = FetchType.EAGER)
    Set<Conversation> conversations = new HashSet<>();
}
