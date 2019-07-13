package com.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.property.ImageStoreProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
    ImageStoreProperties.class
})
public class ImageApplication implements CommandLineRunner {
	
	private static final Logger LOG =
		      LoggerFactory.getLogger(ImageApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ImageApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("===============================================================");
		LOG.info("Application Started");
		LOG.info("===============================================================");
		
	}

	
	

}
