package com.skidata.codingtest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skidata.codingtest.entity.Person;
import com.skidata.codingtest.entity.Telephone;
import com.skidata.codingtest.repository.PersonRepository;

@Transactional
@Component
public class PersonInitializer {

	@Autowired
	PersonRepository personRepository;

	public void init() {
		Person max = new Person("Max", "Mustermann");
		max.getTelephones().add(new Telephone("AT", "0664123456789"));
		personRepository.save(max);
		personRepository.save(new Person("Susi", "Sorglos"));
		personRepository.save(new Person("Robert", "Space"));
	}

}
