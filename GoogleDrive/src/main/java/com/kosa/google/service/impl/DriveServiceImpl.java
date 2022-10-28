package com.kosa.google.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosa.google.dao.DriveDao;
import com.kosa.google.model.DirectoryVO;
import com.kosa.google.model.UploadFileVO;
import com.kosa.google.service.DriveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DriveServiceImpl implements DriveService {

	@Autowired
	DriveDao driveDao;
	
	@Override
	public void uploadFolder(DirectoryVO vo) {
		vo.setId(vo.getId().trim()); //공백 제거
		driveDao.uploadFolder(vo);
	}

	@Override
	public List<DirectoryVO> findDirectory(String id) {
		return driveDao.findDirectory(id);
	}

	@Override
	public void uploadFile(int no,String id, MultipartFile file) {
		try {
			id = id.trim();
			UploadFileVO vo = UploadFileVO.builder()
										.directoryNo(no)
										.fileName(file.getOriginalFilename())
										.fileSize(file.getSize())
										.fileContentType(file.getContentType())
										.fileData(file.getBytes())
										.id(id)
										.build();
			driveDao.uploadFile(vo);
		} catch (IOException e) {
			log.info(e.getMessage());
			throw new RuntimeException();
		}
	}

	@Override
	public List<DirectoryVO> findMyDirectory(String id, int no) {
		return driveDao.findMyDirectory(id, no);
	}

	@Override
	public List<UploadFileVO> findMyFile(String id, int no) {
		return driveDao.findMyFile(id, no);
	}

	@Override
	public UploadFileVO getFile(int fileId) {
		return driveDao.getFile(fileId);
	}

	@Override
	public List<DirectoryVO> findPathList(String id, int no) {
		return driveDao.findPathList(id, no);
	}

	@Override
	public void importantCheck(String id, String type, int key) {
		if(type.equals("file")) {
			if(driveDao.findFileImportant(id, key) == 0) {
				driveDao.updateFileImportant(id, key, 1);
			}else {
				driveDao.updateFileImportant(id, key, 0);
			}
		}else { //directory
			if(driveDao.findDirectoryImportant(id, key) == 0) {
				driveDao.updateDirectoryImportant(id, key, 1);
			}else {
				driveDao.updateDirectoryImportant(id, key, 0);
			}
		}
	}

	@Override
	public List<UploadFileVO> uploadFileImportantList(String id) {
		return driveDao.uploadFileImportantList(id);
	}

	@Override
	public List<DirectoryVO> directoryImportantList(String id) {
		return driveDao.directoryImportantList(id);
	}

	@Override
	public void changeName(String id, String title, String type, String key) {
		if(type.equals("file")) {
			driveDao.changeFileName(id, title, key);
		}else{
			driveDao.changeDirectoryName(id, title, key);
		}
	}

	@Override
	public int findNo(String id) {
		return driveDao.findNo(id);
	}
}
