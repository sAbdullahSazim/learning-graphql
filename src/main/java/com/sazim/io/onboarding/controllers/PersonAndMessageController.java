package com.sazim.io.onboarding.controllers;

import com.sazim.io.onboarding.models.Conversation;
import com.sazim.io.onboarding.models.Message;
import com.sazim.io.onboarding.models.Person;
import com.sazim.io.onboarding.repositories.ConversationRepository;
import com.sazim.io.onboarding.repositories.MessageRepository;
import com.sazim.io.onboarding.repositories.PersonRepository;
import com.sazim.io.onboarding.service.PersonAndMessageService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class PersonAndMessageController {
    private final PersonRepository personRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final PersonAndMessageService personAndMessageService;

    public PersonAndMessageController(PersonRepository personRepository, ConversationRepository conversationRepository, MessageRepository messageRepository, PersonAndMessageService personAndMessageService) {
        this.personRepository = personRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.personAndMessageService = personAndMessageService;
    }

    @QueryMapping
    public List<Person> persons(){
        return personRepository.findAll();
    }

    @MutationMapping
    public Person createAccount(@Argument String name){
        return personRepository.save(Person.builder().name(name).build());
    }

    @MutationMapping
    public Person deleteAccount(@Argument String name){
         Person person = personRepository.findPersonByName(name);
         personRepository.delete(person);
         return person;
    }
    @QueryMapping
    public Set<Conversation> conversations(@Argument Long personID){
        Optional<Person> person = personRepository.findById(personID);
        if(person.isPresent()){
            return person.get().getConversations();
        }else{
            return Collections.emptySet();
        }
    }

    @QueryMapping
    public Set<Message> messages(@Argument Long conversationID){
        Optional<Conversation> conversation = conversationRepository.findById(conversationID);
        if(conversation.isPresent()){
            return conversation.get().getMessages();
        }else{
            return Collections.emptySet();
        }
    }
    @MutationMapping
    public Message sendMessage(@Argument Long fromPersonID, @Argument Long toPersonID, @Argument String text) {
        Person senderPerson = personRepository.findById(fromPersonID).orElseThrow();
        Person receiver = personRepository.findById(toPersonID).orElseThrow();
        return personAndMessageService.sendMessage(senderPerson,receiver,text);
    }
    @MutationMapping
    public Message deleteMessage(@Argument Long messageID){
        Message byId = messageRepository.findById(messageID).orElseThrow();
        messageRepository.delete(byId);
        return byId;
    }
}
