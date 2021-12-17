package com.penske.apps.adminconsole.model;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class ImageFile {
	private static Logger logger = LogManager.getLogger(ImageFile.class);
	
	private int length;
	private byte[] bytes;
	private String name;
	private String type;
	
	public ImageFile() {
		super();
	}
	
	public ImageFile(MultipartFile file) {
		super();
		try{
			byte[] bytes = file.getBytes();
			String contentType = file.getContentType();
			String originalFilename = file.getOriginalFilename();
			
			setBytes(bytes);
			setType(contentType);
			setName(originalFilename);
		} catch (IOException e){
			logger.error(e.getMessage());
		}
	}
	
	// Getters
	public int getLength() {
		return length;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	// Setters
	public void setLength(int length) {
		this.length = length;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBytesAsHex() throws IOException {
		StringBuilder hexString = new StringBuilder();

		for (byte b : bytes) {
			hexString.append(String.format("%02x", b));
		}

		return hexString.toString();
	}
}
