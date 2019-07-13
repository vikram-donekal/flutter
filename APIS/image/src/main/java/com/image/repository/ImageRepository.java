package com.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.image.model.ImageDeatails;

public interface ImageRepository extends JpaRepository<ImageDeatails, Long> {

}
