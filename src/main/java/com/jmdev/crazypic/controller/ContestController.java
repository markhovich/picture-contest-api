package com.jmdev.crazypic.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jmdev.crazypic.dao.ContestRepository;
import com.jmdev.crazypic.exceptions.ContestNotFoundException;
import com.jmdev.crazypic.model.Contest;

@RestController
public class ContestController {
	
	@Autowired
	private ContestRepository cr;

	@GetMapping("/contests")
	public Iterable<Contest> getAllContests(){
		return this.cr.findAll();
	}
	
	@GetMapping("/contests/{id}")
	public Contest getContest(@PathVariable int id) throws ContestNotFoundException{
		Optional<Contest> contest = this.cr.findById(id);
		
		if (!contest.isPresent())
			throw new ContestNotFoundException(id);
		
		return contest.get();
	}
	
	@PostMapping("/contests")
	public ResponseEntity<Object> createStudent(@RequestBody Contest contest) {
		Contest savedStudent = this.cr.save(contest);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/contests/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody Contest contest, @PathVariable int id) {
		Optional<Contest> studentOptional = this.cr.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();
		
		contest.setId(id);
		this.cr.save(contest);

		return ResponseEntity.accepted().build();
	}
	
	@DeleteMapping("/contests/{id}")
	public void deleteStudent(@PathVariable int id) {
		this.cr.deleteById(id);
	}
}
