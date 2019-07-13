package com.image.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exception.ImageNotFoundException;
import com.exception.ImageStorageException;
import com.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.image.model.ImageDeatails;
import com.image.service.ImageService;
import com.image.service.LoginService;
import com.image.util.CommonUtil;

@RestController
@RequestMapping("/api")
public class ImageController {

	private static final Logger LOG =
			LoggerFactory.getLogger(ImageController.class);


	@Autowired
	ImageService imageService;

	@Autowired
	LoginService loginService;

	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam(name="username",required=true) String userName,@RequestParam("password") String password) {
		LOG.info("Rest Request to login");
		return Optional.ofNullable(loginService.checkUser(userName,password))
				.map(result -> new ResponseEntity<>(result,HttpStatus.OK))
				.orElse(new ResponseEntity<>(new UserNotFoundException("Login Failed"),HttpStatus.FORBIDDEN));
	}



	@GetMapping("/images")
	public ResponseEntity<?> getAllImages() {

		LOG.info("Rest Request to getAllImages");

		return Optional.ofNullable(imageService.getAllImages())
				.map(result -> new ResponseEntity<>(result,HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

	@GetMapping("/images/count")
	public ResponseEntity<Long> getCountOfImages() {
		LOG.info("Rest Request to getCountOfImages");
		return Optional.ofNullable(imageService.getCountOfImages())
				.map(result -> new ResponseEntity<>(result,HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}


	@PostMapping("/image")	
	public ResponseEntity<?> uploadImage(@RequestParam(name="image",required=true) MultipartFile image,@RequestPart("imageDetail") String imageDetail) throws JsonParseException, JsonMappingException, IOException {	
		LOG.info("Rest Request to Upload Image");

		return Optional.ofNullable(imageService.uploadImage(image,imageDetail))
				.map(result -> new ResponseEntity<>(result,HttpStatus.OK))
				.orElse(new ResponseEntity<>(new ImageStorageException("Verficatio and Validation of Image Failed"),HttpStatus.NOT_FOUND));

	}

	@GetMapping("/image/{id}")
	public ResponseEntity<?> getImageById(@PathVariable(value = "id") Long imageId) {
		LOG.info("Rest Request to getImageById");

		Optional<ImageDeatails> response =imageService.getImageById(imageId);
		if (response.isPresent()){
			return new ResponseEntity<ImageDeatails>(response.get(),HttpStatus.OK);
		}else{
			throw new ImageNotFoundException("Image Not Found. Id :"+imageId);
		}
	}


	@PutMapping("/image")	
	public ResponseEntity<?> updateImage(@RequestParam(name="image",required=false) MultipartFile image,@RequestPart("imageDetail") String imageDetail) throws JsonParseException, JsonMappingException, IOException {				

		LOG.info("Rest Request to updateImage");

		return Optional.ofNullable(imageService.updateImage(image,imageDetail))
				.map(result -> new ResponseEntity<>(result,HttpStatus.OK))
				.orElse(new ResponseEntity<>(new ImageStorageException("Verficatio and Validation of Image Failed"),HttpStatus.NOT_FOUND));

	}

	@DeleteMapping("/image/{id}")	
	public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long imageId) throws IOException {	

		LOG.info("Rest Request to deleteImage");
		Object response =imageService.deleteImage(imageId);
		if (CommonUtil.isNullOrEmpty(response)){
			throw new ImageNotFoundException("Image Not Found. Id :"+imageId);
		}else{
			return new ResponseEntity<Object>(response,HttpStatus.OK);
		}
	}


	@GetMapping(value = "/image",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody ResponseEntity<?> getImage(@RequestParam(name="imageName")String imageName,@RequestParam(name="type")String type) throws IOException {
		
		
		LOG.info("Rest Request to getImage");
		
		return Optional.ofNullable(imageService.serveImage(imageName,type))
				.map(result -> new ResponseEntity<>(result,HttpStatus.OK))
				.orElse(new ResponseEntity<>(new ImageNotFoundException("Image Name: "+imageName +":::Type: "+type+":::NOT FOUND"),HttpStatus.NOT_FOUND));

		
	}



}