package com.codewithrupaliblog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

//isaki implements class banayege
public interface FileService {
	
	
	String uploadImage(String path, MultipartFile file) throws IOException;

	
	
	InputStream getResource(String path, String fileName) throws FileNotFoundException;

}
