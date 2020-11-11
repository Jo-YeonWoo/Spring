package com.kg.myapp.file.dao;

import com.kg.myapp.file.model.FileVO;

public interface IFileRepository {
	
	int getMaxFileId();
	void uploadFile(FileVO file);
	FileVO getFile(int fileId);

}
