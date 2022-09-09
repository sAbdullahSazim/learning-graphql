package com.sazim.io.onboarding.repositories;

import com.sazim.io.onboarding.models.Conversation;
import com.sazim.io.onboarding.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findPersonByName(String name);
}
