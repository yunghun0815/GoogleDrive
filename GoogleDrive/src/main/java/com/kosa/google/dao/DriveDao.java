package com.kosa.google.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kosa.google.model.DirectoryVO;
import com.kosa.google.model.UploadFileVO;

import lombok.val;

@Repository
@Mapper
public interface DriveDao {
	//기본 디렉토리(회원 가입시 같이)
	public void defaultDirectory(String id);
	//기본 용량(회원가입시 같이)
	public void defaultDrive(String id);
	//폴더 만들기
	public void uploadFolder(DirectoryVO vo); 
	//폴더 타이틀 중복 체크
	public int titleCheck(@Param(value = "id") String id, @Param(value = "title")String title); 
	//폴더 목록 보여주기
	public List<DirectoryVO> findDirectory(String id);
	//파일 업로드
	public void uploadFile(UploadFileVO vo);
	//내 폴더 조회
	public List<DirectoryVO> findMyDirectory(@Param(value = "id") String id, @Param(value = "no") int no);
	//내 파일 조회
	public List<UploadFileVO> findMyFile(@Param(value = "id") String id, @Param(value = "no") int no);
	//파일 다운로드
	public UploadFileVO getFile(int fileId);
	//상위폴더 리스트
	public List<DirectoryVO> findPathList(@Param(value = "id")String id, @Param(value="no")int no);
	//중요도 체크
	public int findFileImportant(@Param(value = "id") String id, @Param(value="key") int key);
	//중요도 업데이트
	public void updateFileImportant(@Param(value = "id") String id, @Param(value="key") int key,  @Param(value="important") int important);
	//중요도 체크
	public int findDirectoryImportant(@Param(value = "id") String id, @Param(value="key") int key);
	//중요도 업데이트
	public void updateDirectoryImportant(@Param(value = "id") String id, @Param(value="key") int key, @Param(value="important") int important);
	//중요 파일 리스트 
	public List<UploadFileVO> uploadFileImportantList(String id);
	//중요 폴더 리스트
	public List<DirectoryVO> directoryImportantList(String id);
	//파일 이름 수정
	public void changeFileName(@Param(value = "id")String id, @Param(value = "title")String title, @Param(value = "key")String key);
	//폴더 이름 수정
	public void changeDirectoryName(@Param(value = "id")String id, @Param(value = "title")String title, @Param(value = "key")String key);
	public String findTitle(int upperNo);
	//제일 상위 번호 찾기
	public int findNo(String id);
}
