package com.skidata.codingtest.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String firstName;

	private String lastName;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Telephone> telephones = new ArrayList<>();

	private Person() {

	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
