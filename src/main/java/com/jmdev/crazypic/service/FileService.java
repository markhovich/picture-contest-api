package com.jmdev.crazypic.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jmdev.crazypic.model.Picture;

@Service
public class FileService {

	@Autowired
	private PictureService ps;
	
	//Where we store the pictures
	private String ROOT_LOCATION = "src/main/resources/pictures/upload/";
	
	//The right repository was created while we created the contest and then we upload the image file there
	public String storeFile(MultipartFile file, String id) throws IOException {
		Path filePath = Paths.get(ROOT_LOCATION + id + "/" + file.getOriginalFilename());

		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		return file.getOriginalFilename();
	}

	public Resource loadFile(int id) throws MalformedURLException {
		Optional<Picture> pic = this.ps.findById(id);

		Path fileLocation = Paths.get(this.ROOT_LOCATION + pic.get().getContest().getId());
		Path file = fileLocation.resolve(pic.get().getFilename());
		Resource resource = new UrlResource(file.toUri());
		System.out.println(resource);
		if(resource.exists() || resource.isReadable()) {
			return resource;
		} else {
			throw new RuntimeException("FAIL TO READ FILE");
		}
	}
}
