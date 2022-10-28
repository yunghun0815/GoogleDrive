package com.kosa.google.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UploadFileVO {
	private int fileId;
	private int directoryNo;
	private String fileName;
	private long fileSize;
	private String fileContentType;
	private Date fileUploadDate;
	private byte[] fileData;
	private String id;
	private int important; //체크하면1 아니면0
	
}
