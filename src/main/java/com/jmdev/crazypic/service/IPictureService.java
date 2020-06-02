package com.jmdev.crazypic.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jmdev.crazypic.dao.PictureRepository;
import com.jmdev.crazypic.model.Contest;
import com.jmdev.crazypic.model.Picture;

@Service
public class IPictureService implements PictureService{

	@Autowired
	private PictureRepository pr;

	@PersistenceContext
	EntityManager em;

	//Where we store the pictures
	private String FILE_DIRECTORY = "src/main/resources/pictures/upload/";

	public Iterable<Picture> findAll(){
		return this.pr.findAll();
	}

	@Override
	public Optional<Picture> findById(int id) {
		return this.pr.findById(id);
	}

	public List<Picture> saveMultiplePictures(){
		return null;
	}
	@Override
	public Picture save(MultipartFile file, String id, String name, String comment, String photograph) throws IOException {

		String url = this.storeFile(file, id).toString();
		Contest contest = new Contest(Integer.parseInt(id));
		Picture picture = new Picture(url, name, file.getOriginalFilename(), photograph, comment, contest);
		return this.pr.save(picture);
	}

	//If the repository doesn't exist, we create it and upload the image file there
	public Path storeFile(MultipartFile file, String id) throws IOException {
		new File(FILE_DIRECTORY + id).mkdir();
		Path filePath = Paths.get(FILE_DIRECTORY + id + "/" + file.getOriginalFilename());

		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		return filePath;
	}

	@Override
	public void deleteById(int id) {
		this.pr.deleteById(id);		
	}

	@Override
	public List<Picture> getPicturesByContest(int id) {
		return this.em.createQuery("FROM Picture WHERE contest_id='" + id + "'").getResultList();
	}

	@Override
	public Picture vote(Picture picture, int note) {
		picture.setNote(picture.getNote() + note);
		return this.pr.save(picture);
	}

}
