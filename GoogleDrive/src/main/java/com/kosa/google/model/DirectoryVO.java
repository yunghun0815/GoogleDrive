package com.kosa.google.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class DirectoryVO {
	private int no;
	private String title;
	private String id;
	private Date uploadDate;
	private int important;
	private int upperNo;
}
