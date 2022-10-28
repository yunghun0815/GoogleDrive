package com.kosa.google.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kosa.google.model.DirectoryVO;
import com.kosa.google.model.UploadFileVO;

public interface DriveService {
	public void uploadFolder(DirectoryVO vo);
	public List<DirectoryVO> findDirectory(String id);
	public void uploadFile(int no, String id, MultipartFile file);
	public List<DirectoryVO> findMyDirectory(String id, int no);
	public List<UploadFileVO> findMyFile(String id, int no);
	public UploadFileVO getFile(int fileId);
	public List<DirectoryVO> findPathList(String id, int no);
	public void importantCheck(String id, String type, int key);
	public List<UploadFileVO> uploadFileImportantList(String id);
	public List<DirectoryVO> directoryImportantList(String id);
	public void changeName(String id, String title, String type, String key);
	public int findNo(String id);
}
