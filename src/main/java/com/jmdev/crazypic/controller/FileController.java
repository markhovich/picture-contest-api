package com.jmdev.crazypic.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jmdev.crazypic.model.Picture;
import com.jmdev.crazypic.payload.UploadFileResponse;
import com.jmdev.crazypic.service.FileService;

@RestController
public class FileController {

	@Autowired
	private FileService fs;
	
	@GetMapping(value="/files")
	public ResponseEntity<List<String>> getAllPictures(){
		return null;
	}
	
	/* Inspiration :
	 * https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
	*/
	@GetMapping(value="/files/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> getPicture(@PathVariable("id") int id, HttpServletRequest request) throws IOException {
		Resource resource = this.fs.loadFile(id);
		
		String contentType = null;
		
		try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }
		
		if(contentType == null) {
			contentType="application/octet-stream";
		}

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@PostMapping(value="/upload", headers="content-type=multipart/form-data")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
						   @RequestParam("id") String id) throws IOException {

		String filename = this.fs.storeFile(file, id);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(file.getName())
                .toUriString();
		
		return new UploadFileResponse(filename, fileDownloadUri,
                file.getContentType(), file.getSize());
	}
	
	@PostMapping(value="/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
			  						@RequestParam("id") String id) {
		return Arrays.asList(files)
                .stream()
                .map(file -> {
					try {
						return this.uploadFile(file, id);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				})
                .collect(Collectors.toList());
	}
}
