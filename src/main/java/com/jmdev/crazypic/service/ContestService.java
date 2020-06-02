package com.jmdev.crazypic.service;

import java.util.List;
import java.util.Optional;

import com.jmdev.crazypic.model.Contest;

public interface ContestService {

	public Iterable<Contest> findAll();
	public List<Contest> findOver();
	public List<Contest> findPending();
	public Optional<Contest> findById(int id);
	public List<Contest> findByToken(String token);
	public Contest save(Contest contest);
	public void deleteById(int id);
}
