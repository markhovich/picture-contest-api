package com.jmdev.crazypic.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmdev.crazypic.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Integer>{

}
