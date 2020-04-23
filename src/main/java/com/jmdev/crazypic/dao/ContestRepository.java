package com.jmdev.crazypic.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmdev.crazypic.model.Contest;

public interface ContestRepository extends JpaRepository<Contest, Integer> {

}
