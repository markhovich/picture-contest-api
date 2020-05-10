package com.jmdev.crazypic.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix="file")
public @Data class FileStorageProperties {

	private String uploadDir;
	
}
