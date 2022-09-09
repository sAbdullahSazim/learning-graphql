package com.sazim.io.onboarding.models;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Conversation extends AbstractPersistable<Long> {
    @ManyToOne
    private Person person;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conversation", orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<Message> messages = new HashSet<>();
    public void addMessage(Message message){
        messages.add(message);
    }
}
