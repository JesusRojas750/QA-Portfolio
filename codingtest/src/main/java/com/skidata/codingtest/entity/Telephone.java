package com.skidata.codingtest.entity;

import java.util.UUID;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
public class Telephone {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String countyCode;

	private String number;

	private Telephone() {
	}

	public Telephone(String countryCode, String number) {
		this.countyCode = countryCode;
		this.number = number;
	}
}
