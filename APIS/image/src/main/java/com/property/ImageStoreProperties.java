package com.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "image")
public class ImageStoreProperties {
    private String uploadDir;
    private String allowedFileTypes;
    private String size;
    private String templateDir;
    
    

    public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public void setAllowedFileTypes(String allowedFileTypes) {
		this.allowedFileTypes = allowedFileTypes;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public String getAllowedFileTypes() {
		return allowedFileTypes;
	}

    public String getSize() {
		return size;
	}

	
	public String getUploadDir() {
        return uploadDir;
    }


    
}