package com.image.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.image.model.ImageDeatails;
import com.image.repository.ImageRepository;
import com.image.util.CommonUtil;
import com.property.ImageStoreProperties;

@Service
public class ImageService {


	@Autowired
	ImageStoreProperties imageStoreProp;

	@Autowired
	CommonUtil commonUtil;


	private static final Logger LOG =
			LoggerFactory.getLogger(ImageService.class);

	@Autowired
	ImageRepository imageRepo;


	public List<ImageDeatails> getAllImages() {		
		return imageRepo.findAll();
	}

	public Object uploadImage(MultipartFile image, String imageDetail) throws JsonParseException, JsonMappingException, IOException {


		ObjectMapper mapper = new ObjectMapper();
		ImageDeatails imageDetailrequest=mapper.readValue(imageDetail, ImageDeatails.class);

		LOG.info("Image Uploading ...");

		if (commonUtil.validateImage(image)){
			LOG.info("Image is Validated ...");
			String imagePath=commonUtil.storeImage(image);
			LOG.info("Image Path ------------>"+imagePath);
			imageDetailrequest.setPath(imagePath);
			String compressedImagePath=commonUtil.storeCommpressedImage(imagePath);
			imageDetailrequest.setCompressed_path(compressedImagePath);			
			return imageRepo.save(imageDetailrequest);
		}
		else
		{
			LOG.error("Verfication - Validation Of Image Failed ..");
			return null;
		}	


	}

	public Optional<ImageDeatails> getImageById(Long imageId) {
		LOG.info(" Image By Id :"+imageId);
		return imageRepo.findById(imageId);
	}

	public Object updateImage(MultipartFile image,String imageDetail) throws JsonParseException, JsonMappingException, IOException {		
		return uploadImage(image,imageDetail);			
	}

	public Object deleteImage(Long imageId) throws IOException {
		LOG.info("Deltion of Image :"+imageId );

		Optional<ImageDeatails> image=imageRepo.findById(imageId);
		if (! image.isPresent()){
			return null;
		}else{			
			LOG.info("Deleting images,Compressed Image");
			List<String> paths= new ArrayList<String>();
			paths.add(imageStoreProp.getUploadDir()+File.pathSeparator+image.get().getPath());
			paths.add(imageStoreProp.getTemplateDir()+File.pathSeparator+image.get().getCompressed_path());

			commonUtil.deleteImages(paths);
			imageRepo.deleteById(imageId);
			return "Image Deleted";
		}
	}

	public long getCountOfImages() {
		LOG.info("Get Count Of Images");		
		return imageRepo.count();
	}

	public Object serveImage(String imageName, String type) throws IOException {

		String imageDir="";
		if (type.equals("compressed")){
			imageDir=imageStoreProp.getTemplateDir();
		}else{
			imageDir=imageStoreProp.getUploadDir();
		}
		LOG.info("Image Dir : "+imageDir );
		LOG.info("imageName : "+imageName );
		LOG.info("Type : "+type );		
		
		 InputStream stream = new FileInputStream(imageDir+File.separator +imageName);
		  byte[] output = IOUtils.toByteArray(stream);

		if (CommonUtil.isNullOrEmpty(output)){
			return null;
		}else{
			return output;
		}

	}

}
