package com.skidata.codingtest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.skidata.codingtest.entity.Person;
import com.skidata.codingtest.entity.Telephone;
import com.skidata.codingtest.service.TelephoneBookService;

@RestController
public class TelephoneBookController {

	@Autowired
	TelephoneBookService telephoneBookService;

	@GetMapping("/ping")
	String ping() {
		throw new NullPointerException();
	}

	@GetMapping("/person/list")
	List<Person> findAll() {
		return telephoneBookService.findAll();
	}

	@GetMapping("/person/findByFirstName/{firstName}")
	Person findPersonByFirstName(@PathVariable String firstName) {
		return telephoneBookService.findByFirstName(firstName);
	}

	@GetMapping("/person/findByLastName/{lastName}")
	Person findPersonByLastName(@PathVariable String lastName) {
		return telephoneBookService.findByLastName(lastName);
	}

	@GetMapping("/person/findByNumber/{phoneNumber}")
	List<Person> findPersonByPhoneNumber(@PathVariable String phoneNumber) {
		return telephoneBookService.findPersonByPhoneNumber(phoneNumber);
	}

	@PostMapping("/person")
	Person addPerson(@RequestBody Person person) {
		return telephoneBookService.savePerson(person);
	}

	//Implementation of the update person functionality following the existent format 
	/**
	 * @param id the UUID of the person to be updated
	 * @param person the updated person details
	 * @return the updated person
	 */
	@PutMapping("/person/{id}")
	Person updatePerson(@PathVariable String id, @RequestBody Person person) {
		return telephoneBookService.updatePerson(UUID.fromString(id), person);
	}

	@PutMapping("/person/{id}/telephone")
	Person addPhoneNumber(@PathVariable String id, @RequestBody Telephone telephone) {
		return telephoneBookService.addTelephone(UUID.fromString(id), telephone);
	}
	
	//Implementation of the delete person functionality following the existent format 
	/**
	 * @param id the UUID of the person to be deleted
	 */
	@DeleteMapping("/person/{id}/delete")
	void deletePerson(@PathVariable String id) {
		telephoneBookService.deletePerson(UUID.fromString(id));
	}
	
	//Implementation of the delete method to remove a phone number by its ID from a Person, following the existent format 
	/**
	 * @param id the UUID of the person from whom the telephone number will be deleted
	 * @param telephoneId the UUID of the telephone number to be deleted
	 */
	@DeleteMapping("/person/{id}/telephone/{telephoneId}/delete")
	void deletePersonTelephone(@PathVariable String id, @PathVariable String telephoneId) {
		 telephoneBookService.removeTelephone(UUID.fromString(id), UUID.fromString(telephoneId));
	}
	
	//case-insensitive method to find persons when only a part of the names or numbers are entered, partial matches possible
	/**
	 * @param searchTerm the term used to search for persons by first name, last name, or phone number
	 * @return a list of persons that match the search criteria
	 */
	 @GetMapping("/person/findByFirstNameOrLastNameOrPhoneNumber/{searchTerm}")
	    public List<Person> findByFirstNameOrLastNameOrPhoneNumber(@PathVariable String searchTerm) {
	        return telephoneBookService.findByFirstNameOrLastNameOrPhoneNumber(searchTerm);
	    }

}
