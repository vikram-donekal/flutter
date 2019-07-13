package com.image.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;import java.util.function.Consumer;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.property.ImageStoreProperties;

@Component
public class CommonUtil {


	@Autowired
	ImageStoreProperties imagePropertiesFromEnv;

	private static final Logger Log =
			LoggerFactory.getLogger(CommonUtil.class);



	private CommonUtil(){

	}

	public static Boolean isNullOrEmpty(Object object){

		if (object == null){
			return true;
		}else if ( object instanceof String  && ( ((String) object).trim().length() == 0)){
			return true;
		}else if ( object instanceof Collection && ( (Collection<?>) object).size() == 0){
			return true;
		}
		else if ( object instanceof Map  && (((Map<?,? >) object )).size() ==0){
			return true;
		}
		return false;

	}


	public  Boolean validateImage(MultipartFile image){
		Boolean Validation=true;
		if (! isNullOrEmpty(image)){	

			String receivedImageName = image.getOriginalFilename();
			int lastIndex = receivedImageName.lastIndexOf('.');
			Long fileSize = ((image.getSize())/ 1024);
			String fileExtension = receivedImageName.substring(lastIndex+1);

			if (fileSize > Long.valueOf(imagePropertiesFromEnv.getSize())){
				Validation=false;
				Log.error("Image Size is more than Allowed ");
				Log.info("Current Image Size : "+ fileSize  + "\nAllowed File Size"+Long.valueOf(imagePropertiesFromEnv.getSize()) );

			}
			if (! imagePropertiesFromEnv.getAllowedFileTypes().contains(fileExtension)){
				Validation=false;
				Log.error("Image File Extension is not Allowed ");
				Log.info("Current File Extension : "+ fileExtension  + "\nAllowed File Extensions"+imagePropertiesFromEnv.getAllowedFileTypes() );
			}

		}else{
			Log.error("Image not Avaible");
			Validation=false;
		}
		return Validation;
	}


	public  String storeImage(MultipartFile image) throws IOException {
		InputStream p_inputStream = null;
		FileOutputStream fos = null;
		try {
			Log.info("method invoked storeImage");
			String uploadPath = imagePropertiesFromEnv.getUploadDir();
			File dir = new File(uploadPath);
			if (!dir.exists()){
				boolean fileStaus=dir.mkdirs();
				Log.info("created saveImage dir",fileStaus);
			}
			String imageName=getNewImageName();
			File convFile = new File(imageName);
			p_inputStream =  new BufferedInputStream(image.getInputStream());
			fos = new FileOutputStream(uploadPath + File.separator + convFile); // NOSONAR
			byte[] buffer = new byte[1024];
			int noOfBytesRead;
			while ((noOfBytesRead = p_inputStream.read(buffer, 0, buffer.length)) != -1){
				fos.write(buffer, 0, noOfBytesRead);
			}
			fos.flush();
			return imageName;			
		} catch (IOException ioe) {
			Log.error("An IOE exception occurred while writing the file" + ioe);
			return null;
		}

		finally{
			if(null!= p_inputStream){
				p_inputStream.close();
			}
			if(null!= fos){
				fos.close();	
			}
		}
	}

	private String getNewImageName() { // NOSONAR
		Log.info("Enter getNewImageName");
		synchronized (this) {
			return (((new Timestamp(System.currentTimeMillis()).toString()).replace(":",
					"_")).replace(" ", "_")).replace(".", "_");
		}
	}

	public  String storeCommpressedImage(String imageName) throws IOException {

		File dir = new File(imagePropertiesFromEnv.getTemplateDir());
		if (!dir.exists()){
			boolean fileStaus=dir.mkdirs();
			Log.info("created Compressed dir",fileStaus);
		}

		File input = new File(imagePropertiesFromEnv.getUploadDir()+File.separator +imageName);
		BufferedImage image = ImageIO.read(input);

		String CompressedIimageName=getNewImageName();
		File output = new File(imagePropertiesFromEnv.getTemplateDir()+File.separator +CompressedIimageName);
		OutputStream out = new FileOutputStream(output);

		ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(out);
		writer.setOutput(ios);

		ImageWriteParam param = writer.getDefaultWriteParam();
		if (param.canWriteCompressed()){
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(0.05f);
		}

		writer.write(null, new IIOImage(image, null, null), param);

		out.close();
		ios.close();
		writer.dispose();
		return CompressedIimageName;
	}

	public void deleteImages(List<String> paths) throws IOException {
		// TODO Auto-generated method stub
		Log.info("Image Path : "+paths.get(0));
		Log.info("Compressed Image Path : "+paths.get(1));

		for (int i=0 ;i< paths.size();i++){
			try {								 
				Path fileToDeletePath = Paths.get(paths.get(i));
				Files.delete(fileToDeletePath);
			} catch (IOException e) {
				Log.error("Error To delete Image :"+paths.get(i) );
				Log.error("Exception :"+e.getMessage());
			}
		}
	}
}
