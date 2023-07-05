package com.shopmax.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;


@Log
@Service
public class FileService {
	//파일 업로드 
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
		UUID uuid = UUID.randomUUID(); //중복되지 않은 이름을 만든다.
		
		//이미지 1.jpg 이미지 확장자명 구한다.
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //확장자명 
		
		//이미지 이름 생성
		String savedFileName = uuid.toString() + extension; // 파일이름 생성
		

		String fileUploadFullUrl = uploadPath + "/" + savedFileName;
		
		//파일 업로드
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); 
		fos.write(fileData); //출력 스트림에 파일 데이터 입력
		fos.close();	//파일 삭제
		
		return savedFileName;
		
	}
	public void deleteFile(String filePath) throws Exception{
		File deleteFile = new File(filePath);
		
		if(deleteFile.exists()) { //해당 경로에 파일이 있으면
			deleteFile.delete(); //파일 삭제
			log.info("파일을 삭제하였습니다."); //로그 기록을 저장한다
		} else {
			log.info("파일이 존재하지 않습니다."); //로그 기록을삭제한다.
		}
	}
}
