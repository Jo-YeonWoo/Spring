package com.kg.myapp.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kg.myapp.file.dao.IFileRepository;
import com.kg.myapp.file.model.FileVO;

@Service
public class FileService implements IFileService {

	@Autowired
	IFileRepository fileRepository;
	
	@Override
	public void uploadFile(FileVO file) {
		file.setFileId(fileRepository.getMaxFileId() + 1);
		fileRepository.uploadFile(file);
	}

	@Override
	public FileVO getFile(int fileId) {
		return fileRepository.getFile(fileId);
	}

}
