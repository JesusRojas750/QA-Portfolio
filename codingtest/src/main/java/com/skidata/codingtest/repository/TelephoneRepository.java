package com.skidata.codingtest.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skidata.codingtest.entity.Telephone;

public interface TelephoneRepository extends JpaRepository<Telephone, UUID> {
}
