package com.jmdev.crazypic.model;

import javax.persistence.*;

import lombok.Data;


@Entity
@Table(name="picture")
public @Data class Picture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String url;
	private String photograph;
	private String comment;
	private int note;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contest_id")
	private Contest contest;
	
	public Picture() {
	
	}

	public Picture(String url, String photograph, String comment, int note) {
		this.url = url;
		this.photograph = photograph;
		this.comment = comment;
		this.note = note;
	}
	
}
