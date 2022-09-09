package com.sazim.io.onboarding.service;

import com.sazim.io.onboarding.models.Conversation;
import com.sazim.io.onboarding.models.Message;
import com.sazim.io.onboarding.models.Person;
import com.sazim.io.onboarding.repositories.ConversationRepository;
import com.sazim.io.onboarding.repositories.MessageRepository;
import com.sazim.io.onboarding.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonAndMessageService {
    private final PersonRepository personRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public PersonAndMessageService(PersonRepository personRepository, ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.personRepository = personRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public Message sendMessage(final Person sender, final Person receiver, String text) {
        Set<Conversation> sendersConversation = conversationRepository.getConversationsByPerson(sender);
        Set<Conversation> receiversConversation = conversationRepository.getConversationsByPerson(receiver);
        Optional<Conversation> conversationOfSenderWithReceiver = sendersConversation.stream().filter(c -> c.getPerson().equals(receiver)).findFirst();
        Optional<Conversation> conversationOfReceiverWithSender = receiversConversation.stream().filter(c -> c.getPerson().equals(sender)).findFirst();

        Message message = Message.builder().sender(sender).receiver(receiver).text(text).build();
        messageRepository.save(message);

        if (conversationOfSenderWithReceiver.isPresent()) {
            conversationOfSenderWithReceiver.get().addMessage(message);
            conversationRepository.save(conversationOfSenderWithReceiver.get());
        }else{
            sendersConversation.add(Conversation.builder().person(sender).messages(Collections.singleton(message)).build());
            conversationRepository.saveAll(sendersConversation);
        }
        if(conversationOfReceiverWithSender.isPresent()){
            conversationOfReceiverWithSender.get().addMessage(message);
            conversationRepository.save(conversationOfReceiverWithSender.get());
        }else {
            receiversConversation.add(Conversation.builder().person(receiver).messages(Collections.singleton(message)).build());
            conversationRepository.saveAll(receiversConversation);
        }
        return message;
    }
}
