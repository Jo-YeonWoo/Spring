package com.kg.myapp.file.service;

import com.kg.myapp.file.model.FileVO;

public interface IFileService {
	
	void uploadFile(FileVO file);
	FileVO getFile(int fileId);

}
