package com.racek.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
 
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceTemplate  implements StorageService{

	private final Path rootLocation = Paths.get("upload-dir");
	
	@Override
	public void store(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Resource loadFile(String filename) {
		Path file = rootLocation.resolve(filename);
		try {
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new IllegalArgumentException("Failed to load file");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("failed to load file");
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to initialize directory");
		}
	}
}
