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

import com.jmdev.crazypic.dao.PictureRepository;
import com.jmdev.crazypic.exceptions.PictureNotFoundException;
import com.jmdev.crazypic.model.Picture;

@RestController
public class PictureController {
	
	@Autowired
	private PictureRepository pr;

	@GetMapping("/pictures")
	public Iterable<Picture> getAllPictures(){
		return this.pr.findAll();
	}
	
	@GetMapping("/pictures/{id}")
	public Picture getPicture(@PathVariable int id) throws PictureNotFoundException{
		Optional<Picture> picture = this.pr.findById(id);
		
		if (!picture.isPresent())
			throw new PictureNotFoundException(id);
		
		return picture.get();
	}
	
	@PostMapping("/pictures")
	public ResponseEntity<Object> preateStudent(@RequestBody Picture picture) {
		Picture savedStudent = this.pr.save(picture);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/pictures/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody Picture picture, @PathVariable int id) {
		Optional<Picture> studentOptional = this.pr.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();
		
		picture.setId(id);
		this.pr.save(picture);

		return ResponseEntity.accepted().build();
	}
	
	@DeleteMapping("/pictures/{id}")
	public void deleteStudent(@PathVariable int id) {
		this.pr.deleteById(id);
	}
}
