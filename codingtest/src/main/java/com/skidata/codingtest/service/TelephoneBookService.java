package com.skidata.codingtest.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skidata.codingtest.entity.Person;
import com.skidata.codingtest.entity.Telephone;
import com.skidata.codingtest.repository.PersonRepository;
import com.skidata.codingtest.repository.TelephoneRepository;
import com.skidata.codingtest.exception.PersonNotFoundException;
import com.skidata.codingtest.exception.TelephoneNotFoundException;

@Service
public class TelephoneBookService {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private TelephoneRepository telephoneRepository;

	/**
     * Finds all persons in the telephone book. 
     * @return a list of all persons
     */
	public List<Person> findAll() {
		return personRepository.findAll();
	}
	/**
     * Finds a person by their first name.
     * @param firstName the first name of the person to find
     * @return an optional containing the found person
     */
	public Person findByFirstName(String firstName) {
		return personRepository.findByFirstName(firstName);
	}
	
	/**
     * Finds a person by their last name.
     * @param LastName the first name of the person to find
     * @return an optional containing the found person
     */
	public Person findByLastName(String lastName) {
		return personRepository.findByLastName(lastName);
	}
	
	/**
     * Finds a person by their Phone Number.
     * @param phone number of the person to find
     * @return an optional containing the found person
     */
	public List<Person> findPersonByPhoneNumber(String phoneNumber) {
		return personRepository.findByPhone(phoneNumber);
	}

	/**
     * Saves a new person to the telephone book.
     * @param person the person to be saved
     * @return the saved person
     */
	public Person savePerson(Person person) {
		return personRepository.save(person);
	}
	
	/**
     * Deletes a person from the telephone book.
     * @param personId the UUID of the person to be deleted
     */
	public void deletePerson(Person person) {
		personRepository.delete(person);
	}

	/**
     * Adds a telephone number to a person.
     * @param personId the UUID of the person to add the telephone number to
     * @param telephone the telephone number to add
     * @return the updated person with the new telephone number added
     */
	public Person addTelephone(UUID personId, Telephone telephone) {
		Optional<Person> op = personRepository.findById(personId);
		if (op.isPresent()) {
			Person p = op.get();
			p.getTelephones().add(telephone);
			personRepository.save(p);
			return p;
		}else {
			throw new PersonNotFoundException(personId);
			
		}
				
	}
	//Method to Remove a telephone number from a person.
	/**
     * @param personId the UUID of the person to remove the telephone number from
     * @param telephone
     **/
	
	public void removeTelephone(UUID personId, UUID telephoneId) {
	    Optional<Person> op = personRepository.findById(personId);
	    if (op.isPresent()) {
	        Person person = op.get();
	        boolean removed = person.getTelephones().removeIf(t -> t.getId().equals(telephoneId));
	        if (removed) {
	            personRepository.save(person);
	        } else {
	            throw new TelephoneNotFoundException(telephoneId);
	        }
	    } else {
	        throw new PersonNotFoundException(personId);
	    }
	}
	
	/**
     * @param personId the UUID of the person to update data from
     * @param Data of the person to be updated
     * @return the updated person with the new data added
     **/
	public Person updatePerson(UUID personId, Person updatedPerson) {
	    return personRepository.findById(personId)
	        .map(existingPerson -> {
	            existingPerson.setFirstName(updatedPerson.getFirstName());
	            existingPerson.setLastName(updatedPerson.getLastName());
	            
	            // Clear the existing telephones and add the new ones. This is necessary to handle orphaned Telephones 
	            existingPerson.getTelephones().clear();
	            existingPerson.getTelephones().addAll(updatedPerson.getTelephones());
	            
	            return personRepository.save(existingPerson);
	        })
	        .orElseThrow(() -> new PersonNotFoundException(personId));
	}
	
	 public void deletePerson(UUID personId) {
	        Person person = personRepository.findById(personId)
	            .orElseThrow(() -> new PersonNotFoundException(personId));
	        personRepository.delete(person);
	    }
	 
	 //method to list persons matching first name, last name or phone number
		/**
	     * Finds a person by their Phone Number, Last name or FIrst name.
	     * @param searching term of the person to find
	     * @return an optional containing the found person
	     */
	 public List<Person> findByFirstNameOrLastNameOrPhoneNumber(String searchTerm) {
	        List<Person> persons = personRepository.findByFirstNameOrLastNameOrPhoneNumber(searchTerm);
	        if (persons.isEmpty()) {
	            throw new PersonNotFoundException("No persons found matching the search term: " + searchTerm); //exception handling for no results
	        }
	        return persons;
	    }

}
