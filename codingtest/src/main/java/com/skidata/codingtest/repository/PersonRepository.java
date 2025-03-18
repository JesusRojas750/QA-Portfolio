package com.skidata.codingtest.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skidata.codingtest.entity.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
	Person findByFirstName(String firstName);

	Person findByLastName(String lastName);

	@Query("SELECT p FROM Person p JOIN p.telephones as tp WHERE tp.number = :phoneNumber")
	List<Person> findByPhone(@Param("phoneNumber") String phoneNumber);
	
	//implementation of query to search by three terms simultaneously 
	@Query("SELECT p FROM Person p JOIN p.telephones tp WHERE " +
	           "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	           "LOWER(tp.number) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	    List<Person> findByFirstNameOrLastNameOrPhoneNumber(@Param("searchTerm") String searchTerm);
}
