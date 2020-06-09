package com.example.shoponline1.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class ImageServiceImpl implements ImageService {

	

	@Override
	public String uploadImage(MultipartFile file) {
		String imgPath = null;
		
		try {
			
			byte[] bytes = file.getBytes();						
			java.nio.file.Path path = Paths.get("uploads/img/" + file.getOriginalFilename());
			Files.write(path, bytes);
			imgPath = "/uploads/img/" + file.getOriginalFilename().toString();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgPath;

	}

}