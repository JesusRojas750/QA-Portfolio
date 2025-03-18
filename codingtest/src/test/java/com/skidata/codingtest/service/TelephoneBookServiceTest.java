package com.skidata.codingtest.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.skidata.codingtest.entity.Person;
import com.skidata.codingtest.entity.Telephone;
import com.skidata.codingtest.exception.PersonNotFoundException;
import com.skidata.codingtest.repository.PersonRepository;

@SpringBootTest
@Transactional
class TelephoneBookServiceTest {
	private static final String FIRSTNAME = "FirstNameOfTestPerson";
	private static final String LASTNAME = "LastNameOfTestPerson";

	@Autowired
	private TelephoneBookService target;
	@Autowired
	private PersonRepository personRepository;

	@Test
	void findPersonByFirstName() {
		Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		assertEquals(FIRSTNAME, target.findByFirstName(person.getFirstName()).getFirstName());
	}

	@Test
	void findPersonByLastName() {
		Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		assertEquals(LASTNAME, target.findByLastName(person.getLastName()).getLastName());
	}

	@Test
	void savePerson() {
		Person person = new Person(FIRSTNAME, LASTNAME);
		person = target.savePerson(person);
		assertEquals(person.getId(), personRepository.findById(person.getId()).get().getId());
	}

	@Test
	void deletePerson() {
		Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
		assertNotNull(person.getId());
		assertEquals(person.getId(), personRepository.findById(person.getId()).get().getId());
		target.deletePerson(person);
		assertFalse(personRepository.findById(person.getId()).isPresent());
	}

	@Test
	void savePersonWithTelefon() {
		Person person = new Person(FIRSTNAME, LASTNAME);
		person = target.savePerson(person);
		Telephone telephone = new Telephone("AT", "123456789");
		target.addTelephone(person.getId(), telephone);
		Person foundPerson = personRepository.findById(person.getId()).get();
		assertEquals(telephone.getNumber(), foundPerson.getTelephones().get(0).getNumber());
		assertEquals(foundPerson, person);
	}
	
	//This tests positive scenario of finding the recently added person, by phone number, using the endpoint that searches all three fields at once
    @Test
    void findByFirstNameOrLastNameOrPhoneNumber_exactPhoneNumber() {
        Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
        Telephone telephone = new Telephone("AT", "1234567897");
        target.addTelephone(person.getId(), telephone);
        List<Person> foundPersons = target.findByFirstNameOrLastNameOrPhoneNumber("1234567897");
        assertEquals(1, foundPersons.size(), "Should find one person with the exact phone number.");
        assertEquals(person.getId(), foundPersons.get(0).getId(), "Person ID should match.");
    }

    //This tests negative scenario of not finding a person, using the endpoint that searches all three fields at once
    @Test
    void findByFirstNameOrLastNameOrPhoneNumber_noMatches() {
        String searchTerm = "NonExistent";
        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class, () -> {
            target.findByFirstNameOrLastNameOrPhoneNumber(searchTerm);
        }, "Should throw PersonNotFoundException when no matches are found.");
        assertEquals("No persons found matching the search term: " + searchTerm, exception.getMessage(), "Exception message should match.");
    }
    
    //This tests positive scenario of updating person, given the ID 
    @Test
    void updatePersonDetails() {
        Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
        String updatedFirstName = "UpdatedFirstName";
        String updatedLastName = "UpdatedLastName";

        person.setFirstName(updatedFirstName);
        person.setLastName(updatedLastName);

        Person updatedPerson = target.updatePerson(person.getId(), person);

        assertEquals(updatedFirstName, updatedPerson.getFirstName(), "First name should be updated.");
        assertEquals(updatedLastName, updatedPerson.getLastName(), "Last name should be updated.");
    }
    
    //This tests Negative scenario of updating person, given the non existent ID. It includes the exception handling when no person is found 
    @Test
    void updateNonExistentPerson() {
        UUID nonExistentPersonId = UUID.randomUUID();
        Person person = new Person(FIRSTNAME, LASTNAME);

        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class, () -> {
            target.updatePerson(nonExistentPersonId, person);
        });

        assertEquals("Person with ID " + nonExistentPersonId + " not found.", exception.getMessage());
    }
    
    //This tests the functionality to add a telephone number to a person successfully, checking that only one entry is returned
    @Test
    void addTelephoneToPerson() {
        Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
        Telephone telephone = new Telephone("AT", "123456789");

        Person updatedPerson = target.addTelephone(person.getId(), telephone);

        assertEquals(1, updatedPerson.getTelephones().size(), "Person should have one telephone.");
        assertEquals(telephone.getNumber(), updatedPerson.getTelephones().get(0).getNumber(), "Telephone number should match.");
    }
    
    //This tests the negative scenario of trying to add a number to a non existent person
    @Test
    void addTelephoneToNonExistentPerson() {
        UUID nonExistentPersonId = UUID.randomUUID();
        Telephone telephone = new Telephone("AT", "123456789");

        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class, () -> {
            target.addTelephone(nonExistentPersonId, telephone);
        });

        assertEquals("Person with ID " + nonExistentPersonId + " not found.", exception.getMessage());
    }
    
    //This tests the positive scenario of removal a phone number by its ID from a Person
    @Test
    void removeTelephoneById() {
        Person person = personRepository.save(new Person(FIRSTNAME, LASTNAME));
        Telephone telephone = new Telephone("AT", "123456789");
        target.addTelephone(person.getId(), telephone);

        UUID telephoneId = person.getTelephones().get(0).getId();

        target.removeTelephone(person.getId(), telephoneId);

        Person updatedPerson = personRepository.findById(person.getId()).orElse(null);

        assertNotNull(updatedPerson, "Person should still exist.");
        assertTrue(updatedPerson.getTelephones().isEmpty(), "Telephone should be removed.");
    }
    
}
