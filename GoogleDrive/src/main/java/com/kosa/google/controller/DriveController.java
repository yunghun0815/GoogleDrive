package com.kosa.google.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.MultipartFile;

import com.kosa.google.model.DirectoryVO;
import com.kosa.google.model.MemberVO;
import com.kosa.google.model.UploadFileVO;
import com.kosa.google.service.DriveService;
import com.kosa.google.service.MemberService;

@Controller
public class DriveController {
	
	@Autowired
	DriveService driveServiceImpl;
	@Autowired 
	MemberService memberServiceImpl;
	
	//폴더 생성
	@PostMapping("/upload/folder")
	public String uploadFolder(DirectoryVO vo, HttpServletRequest request) {
		String id = vo.getId().trim();
		String referer = request.getHeader("Referer");
		driveServiceImpl.uploadFolder(vo); 
		return "redirect:"+referer;
	}
	
	//회원별 폴더 경로 json형식으로 반환
	@ResponseBody
	@GetMapping("/directory/find")
	public int findDirectory(String id) {
	//	return driveServiceImpl.findDirectory(id);
		return driveServiceImpl.findNo(id);
	}
	
	//파일 업로드
	@PostMapping("/upload/file")
	public String uploadFile(@RequestParam(value = "no") int no, @RequestParam(value ="id") String id,
			@RequestParam MultipartFile file, HttpServletRequest request){
		id = id.trim();
		driveServiceImpl.uploadFile(no, id, file);
		
		String referer = request.getHeader("Referer");
		return "redirect:"+referer;
	}
	
	//내 드라이브 (폴더 + 파일 조회)
	@GetMapping("/drive/0/{id}")
	public String myDrive(@PathVariable String id, @RequestParam(value = "no", required = false) String no, Model model) {
		MemberVO member = memberServiceImpl.memberInfo(id);
		int upperNo = 0;
		if(no==null) {
			 upperNo = driveServiceImpl.findNo(id);
		}else {
			upperNo = Integer.parseInt(no);
		}
		model.addAttribute("name", member.getName());
		  model.addAttribute("directoryList", driveServiceImpl.findMyDirectory(id, upperNo)); 
		  model.addAttribute("fileList", driveServiceImpl.findMyFile(id, upperNo));
		  model.addAttribute("pathList", driveServiceImpl.findPathList(id, upperNo));
		 
		model.addAttribute("id", id);
		model.addAttribute("click", 0);
		return "main";
	}
	//파일 다운로드 
	@GetMapping("/file/{fileId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int fileId){
		try {
			UploadFileVO file = driveServiceImpl.getFile(fileId);
			
			HttpHeaders headers = new HttpHeaders();
			String[] mtypes = file.getFileContentType().split("/");
			headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
			headers.setContentLength(file.getFileSize());
			
		
			String fileName = new String(file.getFileName().getBytes("UTF-8"), "ISO-8859-1");
			headers.setContentDispositionFormData("attachment", fileName);
			return new ResponseEntity<byte[]>(file.getFileData(), headers, HttpStatus.OK);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
			return null; 
		}
	}
	//이름 수정
	@ResponseBody
	@PostMapping("/change/name")
	public String changeName(String id, String type, String title, String key){
		driveServiceImpl.changeName(id, title, type, key); 
		return "CHANGE TITLE COMPLETE";
	}
	
	
	//중요 체크
	@ResponseBody
	@GetMapping("/important/check")
	public String importantCheck(String id, String type, int key) {
		driveServiceImpl.importantCheck(id, type, key);
		return "important check";
	}
	
	//공유 문서함
	@GetMapping("/drive/1/{id}")
	public String shareDrive(@PathVariable String id, Model model) {
		model.addAttribute("click", 1);
		return "main";
	}
	//최근 문서함
	@GetMapping("/drive/2/{id}")
	public String latelyDrive(@PathVariable String id, Model model) {
		model.addAttribute("click", 2);
		return "main";
	}
	//중요 문서함
	@GetMapping("/drive/3/{id}")
	public String importantDrive(@PathVariable String id, Model model) {
		MemberVO member = memberServiceImpl.memberInfo(id);
		
		model.addAttribute("name", member.getName());
		model.addAttribute("fileList", driveServiceImpl.uploadFileImportantList(id));
		model.addAttribute("directoryList", driveServiceImpl.directoryImportantList(id));
		model.addAttribute("click", 3);
		return "main";
	}
	
	/*
	 * //파일 삭제
	 * 
	 * @ResponseBody
	 * 
	 * @PostMapping("/delete/drive") public String deleteDrive(String id, String
	 * type, int key) { driveServiceImpl.deleteDrive(id, type, key); return
	 * "delete success"; }
	 */
	
	//휴지통
	@GetMapping("/drive/4/{id}")
	public String garbageDrive(@PathVariable String id, Model model) {
		model.addAttribute("click", 4);
		return "main";
	}
}
