package com.jmdev.crazypic.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="contest")
public @Data class Contest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;
	private String author;
	private String token;
	private Date deadline;
	@OneToMany(mappedBy="contest", cascade=CascadeType.ALL)
	private List<Picture> pictures;
	
	public Contest() {}
	
	public Contest(int id) {
		this.id = id;
	}

	public Contest(String title, String author, String token) {
		this.title = title;
		this.author = author;
		this.token = token;
	}
	
	public void addPicture(Picture picture) {
		if(this.pictures != null) {
			this.pictures = new ArrayList<Picture>();
		}
	this.pictures.add(picture);
	}
	
	public void removePicture(Picture picture) {
		if(this.pictures != null) {
			this.pictures.remove(picture);
		}
	}
	
	public void setPictures(List<Picture> pictures) {
		this.pictures.clear();
		if(pictures != null) {
			this.pictures.addAll(pictures);
		}
		
	}
	
}
