package com.jmdev.crazypic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name="contest")
public @Data class Contest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String author;
	private String url;
	@OneToMany(mappedBy="contest", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Picture> pictures;
	
	public Contest() {}

	public Contest(String name, String author, String url) {
		this.name = name;
		this.author = author;
		this.url = url;
	}
	
	public void addPicture(Picture picture) {
		if(this.pictures != null) {
			this.pictures = new ArrayList<Picture>();
		}
	this.pictures.add(picture);
	}
	
	
}
