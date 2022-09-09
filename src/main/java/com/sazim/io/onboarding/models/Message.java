package com.sazim.io.onboarding.models;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Message extends AbstractPersistable<Long>{
    @OneToOne
    private Person sender;
    @OneToOne
    private Person receiver;
    private String text;
    @ManyToOne
    Conversation conversation;
}
