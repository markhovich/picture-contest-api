package com.jmdev.crazypic.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Entity
@Table(name="picture")
public @Data class Picture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String url;
	private String photograph;
	private String comment;
	private int note;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contest_id")
	@JsonIgnore
	private Contest contest;
	
	public Picture() {
	
	}

	public Picture(String url, String name, String photograph, String comment, Contest contest) {
		this.url = url;
		this.name = name;
		this.photograph = photograph;
		this.comment = comment;
		this.note = 0;
		this.contest = contest;
	}
	
}
