package com.jmdev.crazypic.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.jmdev.crazypic.model.Picture;

public interface PictureService {

	public Iterable<Picture> findAll();
	public Optional<Picture> findById(int id);
	public void deleteById(int id);
	public List<Picture> getPicturesByContest(int id);
	public Picture vote(Picture picture, int note);
	public List<Picture> saveMultiplePictures();
	public Picture save(MultipartFile file, String id, String name, String comment, String photograph) throws IOException;
}
