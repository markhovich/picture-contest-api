package com.jmdev.crazypic.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jmdev.crazypic.exception.ContestNotFoundException;
import com.jmdev.crazypic.model.Contest;
import com.jmdev.crazypic.service.ContestService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class ContestController {
	
	@Autowired
	private ContestService cs;

	@GetMapping("/contests")
	public Iterable<Contest> getAllContests(){
		return this.cs.findAll();
	}
	
	@GetMapping("/contests/{id}")
	public Contest getContest(@PathVariable int id) throws ContestNotFoundException{
		Optional<Contest> contest = this.cs.findById(id);
		
		if (!contest.isPresent())
			throw new ContestNotFoundException(id);
		
		return contest.get();
	}
	
	@GetMapping("contests/over")
	public List<Contest> getOver(){
		return this.cs.findOver();	
	}
	
	@GetMapping("contests/pending")
	public List<Contest> getPending(){
		return this.cs.findPending();	
	}
	
	@GetMapping("/contest/{token}")
	public Contest getContestByToken(@PathVariable String token) {
		return this.cs.findByToken(token).get(0);
	}
	
	@PostMapping("/contests")
	public ResponseEntity<Object> createContest(@RequestBody Contest contest) {
		Contest savedContest = this.cs.save(contest);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedContest.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/contests/{id}")
	public ResponseEntity<Object> updateContest(@RequestBody Contest contest, @PathVariable int id) {
		Optional<Contest> studentOptional = this.cs.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();
		
		contest.setId(id);
		this.cs.save(contest);

		return ResponseEntity.accepted().build();
	}
	
	@DeleteMapping("/contests/{id}")
	public void deleteContest(@PathVariable int id) {
		this.cs.deleteById(id);
	}
}
