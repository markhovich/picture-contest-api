package com.jmdev.crazypic.service;

import java.io.File;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmdev.crazypic.dao.ContestRepository;
import com.jmdev.crazypic.dao.PictureRepository;
import com.jmdev.crazypic.model.Contest;
import com.jmdev.crazypic.model.Picture;

@Service
public class IContestService implements ContestService {

	@Autowired
	private ContestRepository cr;
	
	@Autowired
	private PictureRepository pr;
	
	@PersistenceContext
	private EntityManager em;
	
	//Where we store the pictures
	private String FILE_DIRECTORY = "src/main/resources/pictures/upload/";
		
	public Iterable<Contest> findAll(){
		return this.cr.findAll();
	}

	public List<Contest> findOver() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		java.sql.Date timeNow = new java.sql.Date(utilDate.getTime());	
		return this.em.createQuery("FROM Contest WHERE deadline < '" + timeNow + "'").getResultList();
	}
	
	public List<Contest> findPending() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		java.sql.Date timeNow = new java.sql.Date(utilDate.getTime());	
		return this.em.createQuery("FROM Contest WHERE deadline > '" + timeNow + "'").getResultList();
	}

	@Override
	public Optional<Contest> findById(int id) {
		return this.cr.findById(id);
	}

	@Override
	public Contest save(Contest contest) {
		
		Contest savedContest = this.cr.save(contest);
		new File(FILE_DIRECTORY + savedContest.getId()).mkdir();

		return savedContest;
	}

	@Override
	public void deleteById(int id) {
		this.cr.deleteById(id);		
	}

	@Override
	public List<Contest> findByToken(String token) {
		return this.em.createQuery("FROM Contest WHERE token ='" + token + "'").getResultList();
	}
}
