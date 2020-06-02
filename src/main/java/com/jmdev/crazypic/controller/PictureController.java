package com.jmdev.crazypic.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jmdev.crazypic.dao.PictureRepository;
import com.jmdev.crazypic.exception.PictureNotFoundException;
import com.jmdev.crazypic.model.Picture;
import com.jmdev.crazypic.service.PictureService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/pictures")
public class PictureController {
	
	@Autowired
	private PictureService ps;

	@GetMapping("")
	public Iterable<Picture> getAllPictures(){
		return this.ps.findAll();
	}
	
	@GetMapping("/{id}")
	public Picture getPicture(@PathVariable int id) throws PictureNotFoundException{
		Optional<Picture> picture = this.ps.findById(id);
		
		if (!picture.isPresent())
			throw new PictureNotFoundException(id);
		
		return picture.get();
	}
	
	@PostMapping(value="", headers="content-type=multipart/form-data")
	public ResponseEntity<Object> createPicture(@RequestParam("file") MultipartFile file, 
												@RequestParam("name") String name,
												@RequestParam("contestId") String contestId,
												@RequestParam("comment") String comment,
												@RequestParam("photograph") String photograph) throws IOException {
		Picture savedPicture = this.ps.save(file, contestId, name, comment, photograph);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPicture.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> voteForPicture(@RequestBody int note, @PathVariable int id) {
		Optional<Picture> pictureOptional = this.ps.findById(id);

		if (!pictureOptional.isPresent())
			return ResponseEntity.notFound().build();
		
		this.ps.vote(pictureOptional.get(), note);

		return ResponseEntity.accepted().build();
	}	
	
	@DeleteMapping("/{id}")
	public void deletePicture(@PathVariable int id) {
		this.ps.deleteById(id);
	}
}
